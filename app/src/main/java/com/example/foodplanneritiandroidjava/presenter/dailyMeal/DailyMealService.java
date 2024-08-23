package com.example.foodplanneritiandroidjava.presenter.dailyMeal;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

public interface DailyMealService {

    void geDailyMeal() ;

    void onAddToFavoritePressed(Meal meal);
}
