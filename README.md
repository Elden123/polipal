## Inspiration
Our world seems increasingly polarized. It feels like everyone is strongly divided on every issue, and no compromise is possible. It's even impossible to discuss politics without being impolite or starting a heated argument.

## What it does
Polipal functions essentially as a moderated discussion between people of opposite political ideologies. It serves to encourage constructive interaction that incentivizes political open-mindedness. The app first collects relevant data from short questionnaires, allowing it to match two people on opposite ends of the political spectrum who otherwise have similar hobbies and interests. It then provides a platform simply for discussion, both of political and non-political topics. During this time, the app analyzes the sentiment statements made by either person. If the discussion is beginning to get heated, the app will warn the participants that they should calm down. After the discussion is finished, if the sentiment analysis shows the discussion went well, the app will show social media information for both users. This allows people who meet on the app to continue their friendship into the future.

## How we built it
The majority of the app was implemented for Android using Android Studio. Data is stored and transferred between users using Firebase, and Amazon Machine Learning was used for sentiment analysis.

## Challenges we ran into
We ran into some difficulty finding an appropriate data set with which to train our machine learning algorithm. Additionally, though most of our team has experience with Android programming, none of us had worked on it very recently. Because of this we had to spend a significant amount of time simply readjusting to the development style. Finally, we faced several issues in implementing the communication over Firebase, mostly because it retrieves data asynchronously.

## Accomplishments we're proud of
We're quite proud, first of all, that we managed to integrate machine learning, having very little prior experience. We are also proud to say that we developed a chat app from scratch, using no templates. It turned out to be quite a task, but in the end we did succeed.

## What we learned
Collectively we learned a significant amount about making machine learning projects. Some of us had a little bit of experience in this area, but none with Amazon Machine Learning specifically. Not only did we learn a lot about machine in general, we developed an accurate model by using over 1 million points of training data.

## What's next for polipal
Some additional back-end improvements would improve scalability. Additionally, some polishing is required before polipal could be released publicly.