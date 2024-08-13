package com.example.foodplanneritiandroidjava.model.network;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.MealsResponse;

import java.util.List;

public interface MealsCallBack {
    void onMealsSuccess(List<Meal> meals);
    void onMealsFailure (String message) ;
}
