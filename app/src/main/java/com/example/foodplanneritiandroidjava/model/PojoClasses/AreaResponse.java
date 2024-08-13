package com.example.foodplanneritiandroidjava.model.PojoClasses;

import java.util.List;

public class AreaResponse {

    private List<Area> meals; // The response contains a list of areas

    public List<Area> getAreas() {
        return meals;
    }

    public void setAreas(List<Area> meals) {
        this.meals = meals;
    }
}
