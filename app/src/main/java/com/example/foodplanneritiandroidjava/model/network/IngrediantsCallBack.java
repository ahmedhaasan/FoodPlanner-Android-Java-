package com.example.foodplanneritiandroidjava.model.network;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;

import java.util.List;

public interface IngrediantsCallBack {
    void onSuccesIngrediants(List<Ingredient> ingredients);
    void onFailIngridiants(String message);
}
