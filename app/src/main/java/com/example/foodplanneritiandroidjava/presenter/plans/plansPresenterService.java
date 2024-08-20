package com.example.foodplanneritiandroidjava.presenter.plans;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;

import java.util.List;

public interface plansPresenterService {

    void insertPlannedMeal(PlannedMeal plannedMeal);
    void deletePlannedMealById(String plannedMealId);
    void getAllPlanned(LiveData<List<PlannedMeal>> plannedMeals);
    void deleteAllPlannedMeals(LiveData<List<PlannedMeal>> plannedMeals);
    void getAllPlannedMealsWithDate(String day);

}
