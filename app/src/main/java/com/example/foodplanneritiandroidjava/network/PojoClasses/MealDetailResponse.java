package com.example.foodplanneritiandroidjava.network.PojoClasses;

/*
EndPoint: lookup.php
Purpose: Represents the detailed information
 about a single meal, returned by the API when querying by meal ID.
 */
public class MealDetailResponse {


    private Meal meal;
    // Getters and Setters

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}

