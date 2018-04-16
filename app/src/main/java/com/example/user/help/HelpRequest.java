package com.example.user.help;

/**
 * Created by USER on 4/3/2018.
 */

public class HelpRequest {
    String requestID, date, start, end, desc, helper, category, status, helpee, notifStatus;

    public HelpRequest(){

    }

    public HelpRequest(String requestID, String category, String date, String start, String end, String desc, String helper, String status, String helpee, String notifStatus) {
        this.requestID = requestID;
        this.date = date;
        this.start = start;
        this.end = end;
        this.desc = desc;
        this.helper = helper;
        this.category = category;
        this.status = status;
        this.helpee = helpee;
        this.notifStatus = notifStatus;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getDate() {
        return date;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDesc() {
        return desc;
    }

    public String getHelper() {
        return helper;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public String getHelpee() {
        return helpee;
    }

    public String getNotifStatus() {
        return notifStatus;
    }

}
