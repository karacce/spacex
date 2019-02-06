package com.ketchup.spacex.data;

import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("mission_patch")
    private String missionPatch;

    public String getMissionPatch() {
        return missionPatch;
    }
}
