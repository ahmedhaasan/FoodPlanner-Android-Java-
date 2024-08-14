package com.example.foodplanneritiandroidjava.model.PojoClasses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsResponse {

	@SerializedName("meals")
	private List<Ingredient> ingredientItems;

	public List<Ingredient> getMeals(){
		return ingredientItems;
	}
}