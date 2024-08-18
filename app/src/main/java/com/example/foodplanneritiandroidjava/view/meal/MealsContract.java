package com.example.foodplanneritiandroidjava.view.meal;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

import java.util.List;

public interface MealsContract {
    void showMeals(List<Meal> meals);
    void showMealsError(String message);
}
