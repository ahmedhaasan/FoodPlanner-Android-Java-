package com.example.foodplanneritiandroidjava.model.PojoClasses;

import java.util.List;

public class AreaResponse {

    private List<Country> countries; // The response contains a list of areas

    public List<Country> getAreas() {
        return countries;
    }

    public void setAreas(List<Country> meals) {
        this.countries = meals;
    }
}
