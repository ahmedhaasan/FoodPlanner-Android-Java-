package com.example.foodplanneritiandroidjava.view.plans;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;

import java.util.List;

public interface PlannedContract {

    void showPlannedMeals(LiveData<List<PlannedMeal>> plannedMeals);
    void showPlannedMealsWithData(LiveData<List<PlannedMeal>> plannedMeals);
    void showPlannedError(String error);
    void onPlannedCliced(PlannedMeal plannedMeal);
    void onPlannedDeleted(PlannedMeal plannedMeal);
    void onPlannedAddedToFavorite(PlannedMeal plannedMeal);
}
