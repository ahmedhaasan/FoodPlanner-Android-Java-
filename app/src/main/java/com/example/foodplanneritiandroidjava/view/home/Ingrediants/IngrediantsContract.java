package com.example.foodplanneritiandroidjava.view.home.Ingrediants;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;

import java.util.List;

public interface IngrediantsContract {

    void showsIngrediants(List<Ingredient> ingredients);
    void showsIngrediantsError(String message);
    void onIngredianImagePressed(String ingrediantName , String SearchType);
}
