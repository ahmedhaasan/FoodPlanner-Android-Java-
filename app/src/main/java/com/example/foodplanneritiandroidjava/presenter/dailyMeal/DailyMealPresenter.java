package com.example.foodplanneritiandroidjava.presenter.dailyMeal;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.view.HomeFragment;

import java.util.List;

public class DailyMealPresenter implements DailyMealService, MealsCallBack {

    // refrence to Remote data source
    MealsRemoteDataSource remoteDataSource ;
    HomeFragment homeFragment ;

    public DailyMealPresenter(MealsRemoteDataSource remoteDataSource, HomeFragment homeFragment) {
        this.remoteDataSource = remoteDataSource;
        this.homeFragment = homeFragment;
    }



    @Override
    public void geDailyMeal() {
    remoteDataSource.makeRandomMealCall(this);
    }

    @Override
    public void onMealsSuccess(List<Meal> meals) {
        homeFragment.showDailyMeals(meals);  // pass the meals to home fragment
    }

    @Override
    public void onMealsFailure(String message) {
        homeFragment.showDailyError(message);  // pass the error to home frag
    }
}
