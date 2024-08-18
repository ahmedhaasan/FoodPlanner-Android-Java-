package com.example.foodplanneritiandroidjava.model.reposatory;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

import java.util.List;

public interface MealParentReposiatoryService {

    public void insertmeal(Meal meal);
    void deleteMeal(Meal meal);
    void addAllLocalMeals(List<Meal> meals);
    void deleteAllLocalMeals(List<Meal> meals);
    LiveData<List<Meal>> getAllLocalMealsMeals();

    // remote
}
