package com.example.foodplanneritiandroidjava.view;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

import java.util.List;

public interface OnDailyMealShows {
    void showDailyMeals(List<Meal> meals);
    void showDailyError(String message);
}
