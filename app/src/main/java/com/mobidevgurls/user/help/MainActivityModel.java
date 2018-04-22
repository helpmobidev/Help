package com.mobidevgurls.user.help;

/**
 * Created by user on 3/12/2018.
 */

public class MainActivityModel {
    private String subCategory, mainCategory;

    public MainActivityModel(String subCategory, String mainCategory) {
        this.subCategory = subCategory;
        this.mainCategory = mainCategory;
    }

    public String getSubCategory() {
        return this.subCategory;
    }

    public String getMainCategory() {
        return this.mainCategory;
    }
}
