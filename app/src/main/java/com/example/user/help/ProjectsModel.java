package com.example.user.help;

/**
 * Created by user on 3/4/2018.
 */

public class ProjectsModel {
    private String category, details;
    //private String date, time; if pwedeng i-concatenate sa projectsacitvity or viewholder

    public ProjectsModel(){

    }

    public ProjectsModel(String category, String details) {
        this.category = category;
        this.details = details;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDetails() {
        return this.details;
    }
}
