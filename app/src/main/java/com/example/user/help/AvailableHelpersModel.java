package com.example.user.help;

/**
 * Created by user on 3/4/2018.
 */

public class AvailableHelpersModel {
    private String helperName, email, category;

    public AvailableHelpersModel(String helperName, String email, String category) {
        this.helperName = helperName;
        this.email = email;
        this.category = category;
    }

    public void setHelperName(String helperName) {
        this.helperName = helperName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getHelperName() {
        return this.helperName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCategory() {
        return this.category;
    }
}
