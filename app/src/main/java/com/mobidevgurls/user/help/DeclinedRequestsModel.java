package com.mobidevgurls.user.help;

/**
 * Created by USER on 4/6/2018.
 */

public class DeclinedRequestsModel {
    private String helpCategory, helpDetails, helpName;

    public DeclinedRequestsModel() {

    }

    public DeclinedRequestsModel(String helpCategory, String helpDetails) {
        this.helpCategory = helpCategory;
        this.helpDetails = helpDetails;
    }

    public void setHelpCategory(String helpCategory) {
        this.helpCategory = helpCategory;
    }

    public void setHelpDetails(String helpDetails) {
        this.helpDetails = helpDetails;
    }

    public String getHelpCategory() {
        return this.helpCategory;
    }

    public String getHelpDetails() {
        return this.helpDetails;
    }
}