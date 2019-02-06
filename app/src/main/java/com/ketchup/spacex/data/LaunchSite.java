package com.ketchup.spacex.data;

import com.google.gson.annotations.SerializedName;

public class LaunchSite {

    @SerializedName("site_name_long")
    private String siteName;

    public String getSiteName() {
        return siteName;
    }
}
