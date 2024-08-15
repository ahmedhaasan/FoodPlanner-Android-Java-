package com.example.foodplanneritiandroidjava.model.network;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Country;

import java.util.List;

public interface CountriesCallBack {
    void onCountriesSuccess(List<Country> countries );
    void onCountriesFails(String message);
}
