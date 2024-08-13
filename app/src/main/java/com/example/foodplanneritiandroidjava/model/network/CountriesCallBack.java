package com.example.foodplanneritiandroidjava.model.network;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Area;

import java.util.List;

public interface CountriesCallBack {
    void onCountriesSuccess(List<Area> countries );
    void onCountriesFails(String message);
}
