package com.example.foodplanneritiandroidjava.model.network;

import android.util.Log;

import com.example.foodplanneritiandroidjava.model.MealApiService;
import com.example.foodplanneritiandroidjava.model.PojoClasses.CategoriesResponse;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;

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
                }else {
                    Log.e("API Response", "Failed to fetch categories, response not successful");
                }

            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable throwable) {
              callBack.onFailurResult(throwable.toString());
              Log.i("Error","network Called Failed"+throwable.getMessage());
            }
        });
    }

}
