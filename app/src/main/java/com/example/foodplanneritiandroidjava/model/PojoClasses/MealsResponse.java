package com.example.foodplanneritiandroidjava.model.PojoClasses;

import java.util.List;

/*
EndPoints:
filter.php
search.php
random.php
Purpose: Represents the response from the API
when fetching a list of meals, based on the category,
 search query, or randomly. It contains a list of Meal objects.
 */
public class MealsResponse {
    private List<Meal> meals;
    // Getters and Setters

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
