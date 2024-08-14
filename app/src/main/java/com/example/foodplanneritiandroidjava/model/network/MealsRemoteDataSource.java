package com.example.foodplanneritiandroidjava.model.network;

import android.util.Log;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Area;
import com.example.foodplanneritiandroidjava.model.PojoClasses.AreaResponse;
import com.example.foodplanneritiandroidjava.model.PojoClasses.CategoriesResponse;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.PojoClasses.IngredientsResponse;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.MealsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSource {
    public static String BASE_URL = "https://themealdb.com/api/json/v1/1/";

    private static MealsRemoteDataSource remoteDataSource = null;

    public static MealsRemoteDataSource getInstance() {  // make a class singltone
        if (remoteDataSource == null) {
            remoteDataSource = new MealsRemoteDataSource();
        }
        return remoteDataSource;
    }


    // intialize retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // make functions to deal with endPoints
    public void makeCategoriesCall(CategoriesCallBack callBack) {
        MealApiService service = retrofit.create(MealApiService.class);
        Call<CategoriesResponse> call = service.getAllCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categories = response.body().getCategories();
                    callBack.onSuccessCategory(categories);
                    // try to print the catigories
                    Log.d("API Response", "Categories fetched successfully: " + categories.size());
                } else {
                    Log.e("API Response", "Failed to fetch categories, response not successful");
                }

            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable throwable) {
                callBack.onFailurResult(throwable.toString());
                Log.i("Error", "network Called Failed" + throwable.getMessage());
            }
        });
    }


    // call random meal
    public void makeRandomMealCall(MealsCallBack mealsCallBack) {
        MealApiService service = retrofit.create(MealApiService.class);
        Call<MealsResponse> call = service.getRandomMeal();   // to return random meal
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                List<Meal> meals = response.body().getMeals();
                mealsCallBack.onMealsSuccess(meals);
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable throwable) {
                mealsCallBack.onMealsFailure(throwable.toString());
            }
        });
    }

    // get meals by Category name
    public void getMealsByCategoryName(MealsCallBack callBack, String category) {


        MealApiService service = retrofit.create(MealApiService.class);
        Call<MealsResponse> call = service.getMealsByCategory(category);
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                List<Meal> meals = response.body().getMeals();
                callBack.onMealsSuccess(meals);
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable throwable) {
                callBack.onMealsFailure(throwable.toString());
            }
        });
    }

    // get meals by meal id
    public void getMealsByCategoryID(MealsCallBack callBack, String id) {


        MealApiService service = retrofit.create(MealApiService.class);
        Call<MealsResponse> call = service.getMealById(id);
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                List<Meal> meals = response.body().getMeals();
                callBack.onMealsSuccess(meals);
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable throwable) {
                callBack.onMealsFailure(throwable.toString());
            }
        });
    }

    // get meal by country

    public void getMealsByCountry(MealsCallBack callBack, String country) {
        MealApiService service = retrofit.create(MealApiService.class);
        Call<MealsResponse> call = service.getMealsByCountry(country);
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                List<Meal> meals = response.body().getMeals();
                callBack.onMealsSuccess(meals);
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable throwable) {
                callBack.onMealsFailure(throwable.toString());
            }
        });
    }

    // get meal by name
    public void getMealsByName(MealsCallBack callBack, String name) {


        MealApiService service = retrofit.create(MealApiService.class);
        Call<MealsResponse> call = service.getMealsByName(name);
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                List<Meal> meals = response.body().getMeals();
                callBack.onMealsSuccess(meals);
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable throwable) {
                callBack.onMealsFailure(throwable.toString());
            }
        });
    }

    public void getAllCountries(CountriesCallBack callBack) {
        MealApiService service = retrofit.create(MealApiService.class);
        Call<AreaResponse> call = service.getAllAreas();
        call.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                List<Area> countries = response.body().getAreas();
                callBack.onCountriesSuccess(countries);
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable throwable) {

                callBack.onCountriesFails(throwable.toString());
            }
        });
    }

    // get Ingridiants
    public void getIngridiants(IngrediantsCallBack callBack) {
        MealApiService service = retrofit.create(MealApiService.class);
        Call<IngredientsResponse> call = service.getIngredients();
        call.enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                List<Ingredient> ingredients = response.body().getIngredientItems();
                callBack.onSuccesIngrediants(ingredients);
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable throwable) {

                callBack.onFailIngridiants(throwable.toString());
            }
        });
    }
}
