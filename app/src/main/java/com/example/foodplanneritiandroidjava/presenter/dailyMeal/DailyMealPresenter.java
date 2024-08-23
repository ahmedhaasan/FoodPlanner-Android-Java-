package com.example.foodplanneritiandroidjava.presenter.dailyMeal;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.home.homeActivity.HomeFragment;

import java.util.List;

public class DailyMealPresenter implements DailyMealService, MealsCallBack {

    // refrence to Remote data source
    MealParentReposiatory reposiatory;
    HomeFragment homeFragment ;

    public DailyMealPresenter(MealParentReposiatory reposiatory, HomeFragment homeFragment) {
        this.reposiatory = reposiatory;
        this.homeFragment = homeFragment;
    }



    @Override
    public void geDailyMeal() {
    reposiatory.makeRandomMealCall(this);
    }

    @Override
    public void onAddToFavoritePressed(Meal meal) {
        reposiatory.insertmeal(meal);
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
