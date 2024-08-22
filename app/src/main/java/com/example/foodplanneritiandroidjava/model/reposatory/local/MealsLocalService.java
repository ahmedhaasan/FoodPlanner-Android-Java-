package com.example.foodplanneritiandroidjava.model.reposatory.local;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;

import java.util.List;

public interface MealsLocalService {

    void insertMeal(Meal meal);
    void deleteMeal(Meal meal);
    void addAllMeals(List<Meal> meals);
    void deleteAllMeals();
    LiveData<List<Meal>> getAllMeals();

    void insertPlannedMeal(PlannedMeal plannedMeal);
    void deletePlannedMealById(String plannedMealId);
    LiveData<List<PlannedMeal>> getAllPlannedMeals();
    LiveData<List<PlannedMeal>> getPlannedMealWithDate(String day);
    void deleteAllPlannedMeals();
    void insertAllPlannedMeals(List<PlannedMeal> meals);  // New method to insert all planned meals

}
