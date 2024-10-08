package com.example.foodplanneritiandroidjava.view.plans;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;

import java.util.List;

public interface PlannedContract {

    LiveData<List<Meal>> showPlannedMeals();

    void onPlannedDeleted(PlannedMeal plannedMeal);
    void onPlannedImageAdded(String mealId);
}


/*
void showPlannedMealsWithData(LiveData<List<PlannedMeal>> plannedMeals);
void showPlannedError(String error);
void onPlannedCliced(PlannedMeal plannedMeal);
void onPlannedAddedToFavorite(PlannedMeal plannedMeal);*/
