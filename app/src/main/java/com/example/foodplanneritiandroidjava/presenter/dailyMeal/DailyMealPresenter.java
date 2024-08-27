package com.example.foodplanneritiandroidjava.presenter.dailyMeal;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.dailyMeals.OnDailyMealContract;

import java.util.List;

public class DailyMealPresenter implements DailyMealService, MealsCallBack {

    // refrence to Remote data source
    MealParentReposiatory reposiatory;
    OnDailyMealContract onDailyMealContract;  // refrence from the interface
                                            // and it will be passed throw the presenter constructor

    public DailyMealPresenter(MealParentReposiatory reposiatory, OnDailyMealContract onDailyMealContract) {
        this.reposiatory = reposiatory;
        this.onDailyMealContract = onDailyMealContract;
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
        onDailyMealContract.showDailyMeals(meals);  // pass the meals to home fragment

    }

    @Override
    public void onMealsFailure(String message) {
        onDailyMealContract.showDailyError(message);  // pass the error to home frag
    }
}
