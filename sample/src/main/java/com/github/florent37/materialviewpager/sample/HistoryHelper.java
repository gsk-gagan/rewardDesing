package com.github.florent37.materialviewpager.sample;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryHelper {
    private final String sampleJson = "[{\"value\":7,\"time_stamp\":\"14-02-2017 20:22\",\"notification_message\":\"Reward Your Hard Work. Keep It Up!!\",\"category\":\"Work\"},{\"value\":14,\"time_stamp\":\"15-02-2017 07:18\",\"notification_message\":\"You don't need a reason to Appreciate Yourself.\",\"category\":\"Others\"},{\"value\":13,\"time_stamp\":\"07-03-2017 21:39\",\"notification_message\":\"Reward Your Hard Work. Keep It Up!!\",\"category\":\"Work\"},{\"value\":2,\"time_stamp\":\"26-02-2017 02:35\",\"notification_message\":\"Spare some change for your savings too!!\",\"category\":\"Leisurely\"},{\"value\":1,\"time_stamp\":\"11-02-2017 15:12\",\"notification_message\":\"Spare some change for your savings too!!\",\"category\":\"Leisurely\"},{\"value\":10,\"time_stamp\":\"22-02-2017 11:14\",\"notification_message\":\"Spare some change for your savings too!!\",\"category\":\"Leisurely\"},{\"value\":17,\"time_stamp\":\"19-02-2017 02:46\",\"notification_message\":\"Reward Your Hard Work. Keep It Up!!\",\"category\":\"Work\"},{\"value\":15,\"time_stamp\":\"17-02-2017 01:24\",\"notification_message\":\"You don't need a reason to Appreciate Yourself.\",\"category\":\"Others\"},{\"value\":18,\"time_stamp\":\"26-02-2017 09:59\",\"notification_message\":\"Spare some change for your savings too!!\",\"category\":\"Leisurely\"},{\"value\":14,\"time_stamp\":\"06-03-2017 22:05\",\"notification_message\":\"Reward Your Hard Work. Keep It Up!!\",\"category\":\"Work\"},{\"value\":12,\"time_stamp\":\"02-03-2017 14:17\",\"notification_message\":\"You don't need a reason to Appreciate Yourself.\",\"category\":\"Others\"},{\"value\":13,\"time_stamp\":\"17-02-2017 04:32\",\"notification_message\":\"You don't need a reason to Appreciate Yourself.\",\"category\":\"Others\"},{\"value\":1,\"time_stamp\":\"07-03-2017 12:25\",\"notification_message\":\"Spare some change for your savings too!!\",\"category\":\"Leisurely\"},{\"value\":20,\"time_stamp\":\"16-02-2017 18:53\",\"notification_message\":\"Conguratulations!! It's time to celebrate and Save.\",\"category\":\"Appreciation\"},{\"value\":7,\"time_stamp\":\"18-02-2017 06:04\",\"notification_message\":\"Reward Your Hard Work. Keep It Up!!\",\"category\":\"Work\"},{\"value\":2,\"time_stamp\":\"24-02-2017 19:14\",\"notification_message\":\"You don't need a reason to Appreciate Yourself.\",\"category\":\"Others\"},{\"value\":8,\"time_stamp\":\"28-02-2017 11:01\",\"notification_message\":\"Conguratulations!! It's time to celebrate and Save.\",\"category\":\"Appreciation\"},{\"value\":10,\"time_stamp\":\"14-02-2017 17:49\",\"notification_message\":\"Spare some change for your savings too!!\",\"category\":\"Leisurely\"},{\"value\":19,\"time_stamp\":\"10-02-2017 21:31\",\"notification_message\":\"Good Job in building your health, now it's time to contribute for wealth.\",\"category\":\"Workout\"},{\"value\":4,\"time_stamp\":\"02-03-2017 23:06\",\"notification_message\":\"Good Job in building your health, now it's time to contribute for wealth.\",\"category\":\"Workout\"},{\"value\":2,\"time_stamp\":\"28-02-2017 19:03\",\"notification_message\":\"Good Job in building your health, now it's time to contribute for wealth.\",\"category\":\"Workout\"},{\"value\":1,\"time_stamp\":\"09-02-2017 03:09\",\"notification_message\":\"Reward Your Hard Work. Keep It Up!!\",\"category\":\"Work\"},{\"value\":7,\"time_stamp\":\"21-02-2017 18:28\",\"notification_message\":\"Conguratulations!! It's time to celebrate and Save.\",\"category\":\"Appreciation\"},{\"value\":7,\"time_stamp\":\"04-03-2017 23:27\",\"notification_message\":\"Reward Your Hard Work. Keep It Up!!\",\"category\":\"Work\"},{\"value\":6,\"time_stamp\":\"28-02-2017 23:04\",\"notification_message\":\"Spare some change for your savings too!!\",\"category\":\"Leisurely\"},{\"value\":2,\"time_stamp\":\"20-02-2017 18:39\",\"notification_message\":\"Good Job in building your health, now it's time to contribute for wealth.\",\"category\":\"Workout\"},{\"value\":1,\"time_stamp\":\"10-02-2017 23:00\",\"notification_message\":\"Good Job in building your health, now it's time to contribute for wealth.\",\"category\":\"Workout\"},{\"value\":4,\"time_stamp\":\"02-03-2017 21:10\",\"notification_message\":\"Good Job in building your health, now it's time to contribute for wealth.\",\"category\":\"Workout\"},{\"value\":19,\"time_stamp\":\"20-02-2017 11:20\",\"notification_message\":\"Reward Your Hard Work. Keep It Up!!\",\"category\":\"Work\"},{\"value\":10,\"time_stamp\":\"11-02-2017 04:38\",\"notification_message\":\"Conguratulations!! It's time to celebrate and Save.\",\"category\":\"Appreciation\"},{\"value\":10,\"time_stamp\":\"05-03-2017 18:50\",\"notification_message\":\"Spare some change for your savings too!!\",\"category\":\"Leisurely\"},{\"value\":6,\"time_stamp\":\"07-03-2017 20:22\",\"notification_message\":\"Good Job in building your health, now it's time to contribute for wealth.\",\"category\":\"Workout\"},{\"value\":20,\"time_stamp\":\"13-02-2017 03:32\",\"notification_message\":\"You don't need a reason to Appreciate Yourself.\",\"category\":\"Others\"},{\"value\":17,\"time_stamp\":\"09-02-2017 13:41\",\"notification_message\":\"Conguratulations!! It's time to celebrate and Save.\",\"category\":\"Appreciation\"},{\"value\":3,\"time_stamp\":\"10-02-2017 04:04\",\"notification_message\":\"Spare some change for your savings too!!\",\"category\":\"Leisurely\"},{\"value\":15,\"time_stamp\":\"01-03-2017 22:56\",\"notification_message\":\"Good Job in building your health, now it's time to contribute for wealth.\",\"category\":\"Workout\"},{\"value\":11,\"time_stamp\":\"08-02-2017 01:23\",\"notification_message\":\"You don't need a reason to Appreciate Yourself.\",\"category\":\"Others\"},{\"value\":2,\"time_stamp\":\"22-02-2017 03:26\",\"notification_message\":\"Conguratulations!! It's time to celebrate and Save.\",\"category\":\"Appreciation\"},{\"value\":14,\"time_stamp\":\"22-02-2017 12:14\",\"notification_message\":\"You don't need a reason to Appreciate Yourself.\",\"category\":\"Others\"},{\"value\":1,\"time_stamp\":\"11-02-2017 18:13\",\"notification_message\":\"Reward Your Hard Work. Keep It Up!!\",\"category\":\"Work\"},{\"value\":8,\"time_stamp\":\"04-03-2017 13:54\",\"notification_message\":\"Conguratulations!! It's time to celebrate and Save.\",\"category\":\"Appreciation\"},{\"value\":13,\"time_stamp\":\"14-02-2017 19:55\",\"notification_message\":\"Spare some change for your savings too!!\",\"category\":\"Leisurely\"},{\"value\":6,\"time_stamp\":\"20-02-2017 11:40\",\"notification_message\":\"You don't need a reason to Appreciate Yourself.\",\"category\":\"Others\"},{\"value\":7,\"time_stamp\":\"13-02-2017 10:50\",\"notification_message\":\"Good Job in building your health, now it's time to contribute for wealth.\",\"category\":\"Workout\"},{\"value\":15,\"time_stamp\":\"19-02-2017 14:30\",\"notification_message\":\"Conguratulations!! It's time to celebrate and Save.\",\"category\":\"Appreciation\"},{\"value\":4,\"time_stamp\":\"24-02-2017 08:00\",\"notification_message\":\"Conguratulations!! It's time to celebrate and Save.\",\"category\":\"Appreciation\"},{\"value\":10,\"time_stamp\":\"28-02-2017 13:09\",\"notification_message\":\"Good Job in building your health, now it's time to contribute for wealth.\",\"category\":\"Workout\"},{\"value\":16,\"time_stamp\":\"07-03-2017 08:15\",\"notification_message\":\"Conguratulations!! It's time to celebrate and Save.\",\"category\":\"Appreciation\"},{\"value\":11,\"time_stamp\":\"09-02-2017 09:49\",\"notification_message\":\"You don't need a reason to Appreciate Yourself.\",\"category\":\"Others\"},{\"value\":4,\"time_stamp\":\"22-02-2017 15:35\",\"notification_message\":\"Reward Your Hard Work. Keep It Up!!\",\"category\":\"Work\"}]";

    private ArrayList<HistoryItem> historyItemList;

    public ArrayList<HistoryItem> getHistoryItemList() {
        return historyItemList;
    }

    public HistoryHelper(String sampleJson) {
        try {
            JSONArray myJsonArray = new JSONArray(sampleJson);
            for(int i=0; i<myJsonArray.length(); i++) {
                 JSONObject myJsonObject = myJsonArray.getJSONObject(i);

                HistoryItem myHistoryItem = new HistoryItem(
                        myJsonObject.getInt("value"),
                        myJsonObject.getString("time_stamp"),
                        myJsonObject.getString("notification_message"),
                        myJsonObject.getString("category")
                );

                historyItemList.add(myHistoryItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HistoryHelper() {
        new HistoryHelper(this.sampleJson);
    }


    public class HistoryItem {
        private float value;
        private Date timeStamp;
        private String notificationMessage;
        private RewardCategory category;


        public float getValue() {
            return value;
        }

        public Date getTimeStamp() {
            return timeStamp;
        }

        public String getNotificationMessage() {
            return notificationMessage;
        }

        public RewardCategory getCategory() {
            return category;
        }

        public HistoryItem(float value, String timeStamp, String notificationMessage, String category) {
            this.value = value;
            try {
                this.timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(timeStamp);
            } catch (ParseException e) {
                this.timeStamp = new Date();
            }
            this.notificationMessage = notificationMessage;

            switch (category) {
                case "Leisurely":
                    this.category = RewardCategory.LEISURELY;
                    break;
                case "Workout":
                    this.category = RewardCategory.WORKOUT;
                    break;
                case "Work":
                    this.category = RewardCategory.HARDWORK;
                    break;
                case "Appreciation":
                    this.category = RewardCategory.APPRECIATION;
                    break;
                default:
                    this.category = RewardCategory.OTHER;
                    break;
            }
        }
    }

    public enum RewardCategory {
        LEISURELY("Leisurely Spending"),
        WORKOUT("Workout Acitvity"),
        HARDWORK("Hard Work"),
        APPRECIATION("Appreciation Event"),
        OTHER("Other Reward");

        private String message;

        private RewardCategory(String inp) {
            this.message = inp;
        }

        @Override
        public String toString() {
            return message;
        }
    }
}
