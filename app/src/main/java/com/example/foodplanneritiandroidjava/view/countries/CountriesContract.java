package com.example.foodplanneritiandroidjava.view.countries;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Country;

import java.util.List;

public interface CountriesContract {
    void showsCountries(List<Country> countries);
    void showCountriesError(String message);
}
