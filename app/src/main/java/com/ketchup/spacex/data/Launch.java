package com.ketchup.spacex.data;

import com.google.gson.annotations.SerializedName;

public class Launch {

    @SerializedName("mission_name")
    private String missionName;

    @SerializedName("launch_year")
    private String launchYear;

    @SerializedName("rocket")
    private Rocket rocket;

    @SerializedName("links")
    private Links links;

    @SerializedName("details")
    private String details;

    @SerializedName("launch_site")
    private LaunchSite launchSite;

    @SerializedName("flight_number")
    private int fligthNumber;

    public LaunchSite getLaunchSite() {
        return launchSite;
    }

    public int getFligthNumber() {
        return fligthNumber;
    }

    public String getDetails() {
        return details;
    }

    public String getMissionName() {
        return missionName;
    }

    public String getLaunchYear() {
        return launchYear;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public Links getLinks() {
        return links;
    }

}
