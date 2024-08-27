package com.example.foodplanneritiandroidjava.presenter.plans;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;

import java.util.List;

public interface plansPresenterService {

    void deletePlannedMealById(String plannedMealId);
    LiveData<List<PlannedMeal>> getAllPlanned();
    void getAllPlannedMealsWithDate(String day);

}
