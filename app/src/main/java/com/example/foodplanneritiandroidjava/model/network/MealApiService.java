package com.example.foodplanneritiandroidjava.model.network;

import com.example.foodplanneritiandroidjava.model.PojoClasses.AreaResponse;
import com.example.foodplanneritiandroidjava.model.PojoClasses.CategoriesResponse;
import com.example.foodplanneritiandroidjava.model.PojoClasses.IngredientsResponse;
import com.example.foodplanneritiandroidjava.model.PojoClasses.MealDetailResponse;
import com.example.foodplanneritiandroidjava.model.PojoClasses.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {
    @GET("categories.php")
    Call<CategoriesResponse> getAllCategories();  // this method returns all categories

    @GET("filter.php")
    Call<MealsResponse> getMealsByCategory(@Query("c") String category);     // this meal returns meals By Category

    @GET("search.php")
    Call<MealsResponse> getMealsByName(@Query("s") String mealName);   // this method returns all meals by name

    @GET("search.php")
    Call<MealsResponse> getAllMealsByFristLetter(@Query("f") String firstLetter); // return a list of meals based on based on frist letter
    @GET("filter.php")
    Call<MealsResponse> getMealsByCountry(@Query("a") String country);  // return a list of meals based on Contry

    @GET("lookup.php")
    Call<MealsResponse> getMealById(@Query("i") String mealId);  // this meathod return detail for selected meal
// take care of above function we my need to make it call<Detailsmeal

    @GET("random.php")
    Call<MealsResponse> getRandomMeal();   // return a random meal

    @GET("list.php?a=list")
    Call<AreaResponse> getAllAreas();   // return a list of areas

    @GET("list.php?i=list")
    Call<IngredientsResponse> getIngredients();

    @GET("filter.php")
    Call<MealsResponse> getMealsByIngredient(@Query("i") String ingredient);




}
