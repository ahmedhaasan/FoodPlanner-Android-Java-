package com.example.foodplanneritiandroidjava.presenter.mealDetails;

import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;

public interface MealDetailsServfice {
    void getMealDetail(String id );
    void insertMealIntoPlanned(PlannedMeal plannedMeal);
}
