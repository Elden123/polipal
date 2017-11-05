package com.github.beijingstrongbow.Communication;

/**
 * Created by stephaniefu on 11/5/2017.
 */

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.machinelearning.model.EntityStatus;
import com.amazonaws.services.machinelearning.model.GetMLModelRequest;
import com.amazonaws.services.machinelearning.model.GetMLModelResult;
import com.amazonaws.services.machinelearning.model.RealtimeEndpointStatus;
import com.example.nolan.polipal.Conversation;
import com.example.nolan.polipal.Home;
import com.example.nolan.polipal.UserData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.amazonaws.services.machinelearning.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.Semaphore;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.machinelearning.AmazonMachineLearningClient;
import com.amazonaws.services.machinelearning.model.GetMLModelRequest;
import com.amazonaws.services.machinelearning.model.GetMLModelResult;
import com.amazonaws.services.machinelearning.model.PredictRequest;
import com.amazonaws.services.machinelearning.model.PredictResult;

import javax.net.ssl.HttpsURLConnection;

public class MessageSentiment {
    private AmazonMachineLearningClient client;
    private String predictEndpoint;


    private DatabaseReference conversationsRef;
    public static MessageSentiment instance;
    private String mlModelId = "";
    private HashMap<String, Float> hm1;
    private int counter = 0;
    private static MessageSentiment ms;
    private Conversation c1;

    public MessageSentiment(String mlModelId1, Conversation c) {
        this.mlModelId = mlModelId1;
        ms = this;
        c1 = c;
        client = new AmazonMachineLearningClient(new BasicAWSCredentials("AKIAJKZS442SYEMO4M7A", "VO6fAC5kFm4eTjJWbo7/bkDh9Y5ZkN/Ir3rcnZhY"));


    }
    public static MessageSentiment getInstance() {
        return ms;
    }
    private HashMap<String, Float> makePrediction(final String msg) {
        final Semaphore s = new Semaphore(0);
        Thread t = new Thread(new Runnable() {
            public void run() {

                GetMLModelRequest modelRequest = new GetMLModelRequest().withMLModelId(mlModelId);
                GetMLModelResult model = client.getMLModel(modelRequest);
                String predictEndpoint = model.getEndpointInfo().getEndpointUrl();

                PredictRequest p = new PredictRequest();
                p.setMLModelId(mlModelId);
                p.addRecordEntry("Var2", msg);
                p.setPredictEndpoint(predictEndpoint);
                HashMap<String, Float> hm = (HashMap) client.predict(p).getPrediction().getPredictedScores();
                hm1 = hm;
                s.release();
            }

        });

        t.start();
        s.acquireUninterruptibly();
        counter++;
        return hm1;
    }
    private float fl = 0;
    private int finalScore = 0;

    public int getFinalScore() {
        return finalScore/counter;
    }

    public void manageScore(String msg) {
        HashMap<String, Float> hm2 = makePrediction(msg);
        int pn = Integer.parseInt(new ArrayList<String>(hm2.keySet()).get(0));
        float confidence = new ArrayList<Float>(hm2.values()).get(0);
        if(pn == 0) confidence = -confidence;
        finalScore += (confidence * 50) + 50;
        if(((confidence * 50) + 50) <= 35) c1.chillWarning();
    }
}
        // https://realtime.machinelearning.us-east-1.amazonaws.com
        /*final Semaphore s = new Semaphore(0);
        conversationsRef = FirebaseDatabase.getInstance().getReference("/Conversations/");
        try {
            URL url = new URL("https://realtime.machinelearning.us-east-1.amazonaws.com");

            HttpsURLConnection c = (HttpsURLConnection) url.openConnection();

            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.setDoInput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));

            out.write('{"MLModelId":"ml-OTXlWUgIjwd", "PredictEndpoint": "https://realtime.machinelearning.us-east-1.amazonaws.com", "Record": {"key1": "value1"}}');
        }
        catch(IOException e) {

        }

        // Initialize the Amazon Cognito credentials provider
        /*final CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-1:2f0823d2-f13b-4ad4-85b9-7f069e7c66ef", // Identity pool ID
                Regions.US_EAST_1 // Region
        );
        final AmazonMachineLearningClient client = new AmazonMachineLearningClient(credentialsProvider);*/
        // Use a created model that has a created real-time endpoint
        /*final String mlModelId = "ml-OTXlWUgIjwd";

        // Call GetMLModel to get the realtime endpoint URL
        final GetMLModelRequest getMLModelRequest = new GetMLModelRequest();
        instance = this;
        Thread t = new Thread(new Runnable() {
            public void run() {

                s.release();
            }

        });

        t.start();
        s.acquireUninterruptibly();*/


    /*public MessageSentiment(final Context context) {
        final Semaphore s = new Semaphore(0);
        conversationsRef = FirebaseDatabase.getInstance().getReference("/Conversations/");

        // Initialize the Amazon Cognito credentials provider
        final CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-1:2f0823d2-f13b-4ad4-85b9-7f069e7c66ef", // Identity pool ID
                Regions.US_EAST_1 // Region
        );
        final AmazonMachineLearningClient client = new AmazonMachineLearningClient(credentialsProvider);
        // Use a created model that has a created real-time endpoint
        final String mlModelId = "ml-OTXlWUgIjwd";

        // Call GetMLModel to get the realtime endpoint URL
        final GetMLModelRequest getMLModelRequest = new GetMLModelRequest();
        instance = this;
        Thread t = new Thread(new Runnable() {
            public void run() {
                String identityId = credentialsProvider.getIdentityId();
                Log.d("LogTag", "my ID is " + identityId);
                getMLModelRequest.setMLModelId(mlModelId);
                GetMLModelResult mlModelResult = client.getMLModel(getMLModelRequest);

                // Validate that the ML model is completed
                if (!mlModelResult.getStatus().equals(EntityStatus.COMPLETED.toString())) {
                    System.out.println("ML Model is not completed: + mlModelResult.getStatus()");
                    return;
                }

                // Validate that the realtime endpoint is ready
                if (!mlModelResult.getEndpointInfo().getEndpointStatus().equals(RealtimeEndpointStatus.READY.toString())) {
                    System.out.println("Realtime endpoint is not ready: " + mlModelResult.getEndpointInfo().getEndpointStatus());
                    return;
                }
                s.release();
            }

        });

        t.start();
        s.acquireUninterruptibly();
    }*/

