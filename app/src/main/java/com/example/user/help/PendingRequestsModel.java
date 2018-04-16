package com.example.user.help;

import java.util.ArrayList;

/**
 * Created by user on 3/12/2018.
 */

public class PendingRequestsModel {
    private String helpCategory, helpDetails;

    public PendingRequestsModel(String helpCategory, String helpDetails) {
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
