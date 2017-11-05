package com.github.beijingstrongbow.Communication;

import com.example.nolan.polipal.UserData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ericd on 11/4/2017.
 */

public class ConversationFactory {

    private DatabaseReference conversationRequests = FirebaseDatabase.getInstance().getReference("/ConversationRequests");

    private UserData thisUser;

    public ConversationFactory(UserData thisUser){
        this.thisUser = thisUser;
    }

    public MessageHandler findConversation(ArrayList<String> topChoices, String time, UserHandler handler){

        handler.goOnline(thisUser, topChoices, time);
        System.out.println("here");
        String[] request = findInRequests();
        System.out.println(request[0] + " " + request[1]);
        if(request[0] != null && !request[1].equals("")){
            handler.removeFromOnline(thisUser);
            conversationRequests.child(thisUser.getUID()).child("messageLocation").removeValue();
            conversationRequests.child(thisUser.getUID()).child("sender").removeValue();
            conversationRequests.child(thisUser.getUID()).removeValue();

            return new MessageHandler(request[1], thisUser.getUID(), request[0], false);
        }
        else {
            String uid = findNewMatch(thisUser, topChoices, time);

            String messageUid = MessageHandler.getNewMessageLocation();

            conversationRequests.child(uid).child("sender").setValue(thisUser.getUID());
            conversationRequests.child(uid).child("messageLocation").setValue(messageUid);

            handler.removeFromOnline(thisUser);

            if(uid.equals("-openopenopenopenope")){
                return new MessageHandler(messageUid, thisUser.getUID(), uid, true);
            }

            return new MessageHandler(messageUid, thisUser.getUID(), uid, false);
        }
    }

    private boolean runOnce = false;

    private String[] findInRequests(){

        final String[] toReturn = new String[2];
        final Semaphore lock = new Semaphore(0);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run(){
                if(!runOnce){
                    try{
                        URL url = new URL("https://polipal-d2e24.firebaseio.com/ConversationRequests.json");

                        HttpsURLConnection c = (HttpsURLConnection) url.openConnection();

                        c.setRequestMethod("GET");
                        c.setDoOutput(false);
                        c.setDoInput(true);

                        BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));

                        String data = in.readLine();
                        System.out.println(data);
                        String[] uidSplit = data.split("\"-");

                        for(int i = 1; i < uidSplit.length; i+=3){
                            String uid = "-" + uidSplit[i].substring(0, 19);
                            if(uid.equals(thisUser.getUID()) || uid.equals("open")){
                                String senderUID = uidSplit[i+2].substring(0, 20);
                                String messageLocation = uidSplit[i+1].substring(0, 20);
                                toReturn[0] = "-" + senderUID.substring(0, senderUID.length()-1);
                                toReturn[1] = "-" + messageLocation.substring(0,messageLocation.length()-1);
                                System.out.println("a " + toReturn[0] + " " + toReturn[1]);
                                lock.release();
                                runOnce = true;
                                return;
                            }
                        }
                    }
                    catch(IOException ex){}

                    lock.release();
                }
            }
        });

        t.start();

        lock.acquireUninterruptibly();

        return toReturn;
    }

    private String findNewMatch(final UserData user, final ArrayList<String> topChoices, final String time){
        final Semaphore lock = new Semaphore(0);
        final StringBuilder toReturn = new StringBuilder();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                    String o_uid = null;
                    ArrayList<String> o_topics = null;
                    String o_time = null;
                    String o_politicalParty = null;
                    int greatestSimilarity = -1; //0-3
                    String gr_uid = null;

                    URL url = new URL("https://polipal-d2e24.firebaseio.com/OnlineUsers.json");

                    HttpsURLConnection c = (HttpsURLConnection) url.openConnection();

                    c.setRequestMethod("GET");
                    c.setDoOutput(false);
                    c.setDoInput(true);

                    BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));

                    String data = in.readLine();
                    System.out.println(data);

                    String[] json = data.split(":|\",|\\},");


                    for(int i = 0; i < json.length - 1; i+=2) {
                        String key = json[i].replaceAll("\"", "");
                        String value = json[i + 1].replaceAll("\"", "");
                        value = value.replaceAll("\\{", "");
                        value = value.replaceAll("\\}", "");
                        if (key.charAt(1) == '-' && !key.equals(user.getUID())) {
                            key = key.replaceAll("\\{", "");
                            o_uid = key;
                        }
                        switch (key) {
                            case "{politicalParty":
                                o_politicalParty = value;
                                break;
                            case "topChoices":
                                o_topics = csvToStringArray(value);
                                break;
                            case "time":
                                o_time = value;
                                break;
                            default:
                                i--;
                                break;
                        }

                        if (o_uid != null && o_time != null && o_topics != null && o_politicalParty != null && !o_uid.equals(user.getUID())) {
                            if (o_time.equals(time) &&
                                    ((o_politicalParty.equals("Conservative") && (user.getPoliticalParty().equals("Liberal") || user.getPoliticalParty().equals("Moderate"))) ||
                                            (o_politicalParty.equals("Liberal") && (user.getPoliticalParty().equals("Conservative") || user.getPoliticalParty().equals("Moderate"))) ||
                                            (o_politicalParty.equals("Moderate") && (user.getPoliticalParty().equals("Liberal") || user.getPoliticalParty().equals("Conservative"))))) {
                                int similarity = 0;

                                for (int j = 0; j < 3; j++) {
                                    for (int k = 0; k < 3; k++) {
                                        System.out.println(o_topics.get(i) + " " + topChoices.get(j));
                                        if (o_topics.get(i).equals(topChoices.get(j))) {
                                            similarity++;
                                        }
                                    }
                                }
                                if (similarity > greatestSimilarity) {

                                    gr_uid = o_uid;
                                    greatestSimilarity = similarity;
                                }
                            }

                            o_uid = null;
                            o_time = null;
                            o_topics = null;
                            o_politicalParty = null;
                        }
                    }

                    if(gr_uid == null){
                        gr_uid = "-openopenopenopenope";
                    }
                    toReturn.append(gr_uid);
                }
                catch(IOException ex){}

                lock.release();
            }
        });

        t.start();

        lock.acquireUninterruptibly();

        return toReturn.toString();
    }

    private ArrayList<String> csvToStringArray(String csv){
        String[] array = csv.split(",");

        ArrayList<String> list = new ArrayList<String>();

        for(int i = 0; i < array.length; i++){
            list.add(array[i]);
        }

        return list;
    }
}
