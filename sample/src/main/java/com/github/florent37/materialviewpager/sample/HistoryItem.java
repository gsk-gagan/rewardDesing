package com.github.florent37.materialviewpager.sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryItem {
    private float value;
    private Date timeStamp;
    private String notificationMessage;
    private HistoryHelper.RewardCategory category;


    public float getValue() {
        return value;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public HistoryHelper.RewardCategory getCategory() {
        return category;
    }

    public HistoryItem(float value, Date timeStamp, String notificationMessage, HistoryHelper.RewardCategory category) {
        this.value = value;
        this.timeStamp = timeStamp;
        this.notificationMessage = notificationMessage;
        this.category = category;
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
                this.category = HistoryHelper.RewardCategory.LEISURELY;
                break;
            case "Workout":
                this.category = HistoryHelper.RewardCategory.WORKOUT;
                break;
            case "Work":
                this.category = HistoryHelper.RewardCategory.HARDWORK;
                break;
            case "Appreciation":
                this.category = HistoryHelper.RewardCategory.APPRECIATION;
                break;
            default:
                this.category = HistoryHelper.RewardCategory.OTHER;
                break;
        }
    }
}
