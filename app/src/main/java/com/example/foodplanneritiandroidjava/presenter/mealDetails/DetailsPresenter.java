package com.example.foodplanneritiandroidjava.presenter.mealDetails;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.home.details.MealDetailsFragment;

import java.util.List;

public class DetailsPresenter implements MealDetailsServfice  , MealsCallBack {

    MealParentReposiatory reposiatory ;
    MealDetailsFragment  mealDetailsFragment ;


    public DetailsPresenter(MealParentReposiatory reposiatory, MealDetailsFragment mealDetailsFragment) {
        this.reposiatory = reposiatory;
        this.mealDetailsFragment = mealDetailsFragment ;
    }

    @Override
    public void getMealDetail(String id) {
        reposiatory.getMealsById(this,id);
    }


    @Override
    public void onMealsSuccess(List<Meal> meals) {
        mealDetailsFragment.onShowDetails(meals);
    }

    @Override
    public void onMealsFailure(String message) {

    }

    // handel when user press on method to add to db;
    @Override
    public void insertMealIntoPlanned(PlannedMeal plannedMeal) {

        reposiatory.insertPlannedMeal(plannedMeal);
    }
}
