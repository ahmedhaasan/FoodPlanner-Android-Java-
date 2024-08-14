package com.example.foodplanneritiandroidjava.model.PojoClasses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {
    @SerializedName("meals")
    private List<Country> country;

    public List<Country> getCountry() {
        return country;
    }
}
