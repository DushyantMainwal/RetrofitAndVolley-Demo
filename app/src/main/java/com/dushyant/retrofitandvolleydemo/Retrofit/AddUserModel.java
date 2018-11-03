package com.dushyant.retrofitandvolleydemo.Retrofit;

import com.google.gson.annotations.SerializedName;

public class AddUserModel {

    @SerializedName("name")
    public String name;
    @SerializedName("job")
    public String job;
    @SerializedName("id")
    public String id;
    @SerializedName("createdAt")
    public String createdAt;

    public AddUserModel(String name, String job) {
        this.name = name;
        this.job = job;
    }


}