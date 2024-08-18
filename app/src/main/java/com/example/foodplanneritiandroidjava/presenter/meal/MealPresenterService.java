package com.example.foodplanneritiandroidjava.presenter.meal;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

public interface MealPresenterService {
    void getMeals();
    void onFavClicked(Meal meal);
    void onAddToPlanClicked(Meal meal);
}
