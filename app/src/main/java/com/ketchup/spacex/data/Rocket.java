package com.ketchup.spacex.data;

import com.google.gson.annotations.SerializedName;

public class Rocket {

    @SerializedName("rocket_name")
    private String name;

    @SerializedName("rocket_type")
    private String type;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
