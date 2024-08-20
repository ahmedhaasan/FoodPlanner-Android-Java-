package com.example.foodplanneritiandroidjava.presenter.plans;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.plans.PlansFragment;

import java.util.List;

public class PlannedPresenter implements plansPresenterService {


    MealParentReposiatory reposiatory;
    PlansFragment plansFragment;

    public PlannedPresenter(MealParentReposiatory reposiatory, PlansFragment plansFragment) {
        this.reposiatory = reposiatory;
        this.plansFragment = plansFragment;
    }

    @Override

    public void insertPlannedMeal(PlannedMeal plannedMeal) {

        reposiatory.insertPlannedMeal(plannedMeal);
    }

    @Override
    public void deletePlannedMealById(String plannedMealId) {
        reposiatory.deletePlannedMealById(plannedMealId);
    }


    @Override
    public void getAllPlanned(LiveData<List<PlannedMeal>> plannedMeals) {

        plansFragment.showPlannedMeals(plannedMeals);
    }

    @Override
    public void deleteAllPlannedMeals(LiveData<List<PlannedMeal>> plannedMeals) {

        reposiatory.deleteAllPlannedMeals();
    }

    @Override
    public void getAllPlannedMealsWithDate(String day) {
        reposiatory.getMealsPlannedByDate(day);
    }
}
