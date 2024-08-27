package com.example.foodplanneritiandroidjava.view.search;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Country;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

import java.util.List;

public interface SearchContract {

    void onSearchSuccess(List<Meal> searchedMeals);
    void onSearchFail(String message);
    void showSuccessCategories(List<Category> categories);
    void showFailCatigories(String message);
    void showSuccessCountries(List<Country> countries);
    void showFailCountries(String message);
    void showSuccessIngrediants(List<Ingredient> ingredients);
    void showFailIngrediants(String message);

}
