package com.example.foodplanneritiandroidjava.model.network;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

public interface MealDetailsCallBack {
    void onMealDetailsSuccess(Meal meal);
    void onMealDetailFails(String messag);
}
