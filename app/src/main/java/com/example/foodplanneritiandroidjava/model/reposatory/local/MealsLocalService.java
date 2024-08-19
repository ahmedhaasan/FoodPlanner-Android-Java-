package com.example.foodplanneritiandroidjava.model.reposatory.local;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

import java.util.List;

public interface MealsLocalService {

    void insertMeal(Meal meal);
    void deleteMeal(Meal meal);
    void addAllMeals(List<Meal> meals);
    void deleteAllMeals(List<Meal> meals);
    LiveData<List<Meal>> getAllMeals();
}
