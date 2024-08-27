package com.example.foodplanneritiandroidjava.presenter.meal;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

public interface MealPresenterService {
    void getMealsByFristLetter(String fristLetter);
    void onFavClicked(Meal meal);
    void getMealsByCategoryName(String categoryName);
    void getMealsByCountryName(String countryName);
    void getMealsByIngrediantsName(String ingrediantName);
    void getMealsByMealName(String mealName);
    void getMealsByID(String mealID);
    void getRandomMeal();

}
