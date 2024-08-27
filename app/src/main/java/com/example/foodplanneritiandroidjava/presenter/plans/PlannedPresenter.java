package com.example.foodplanneritiandroidjava.presenter.plans;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.plans.PlannedContract;
import com.example.foodplanneritiandroidjava.view.plans.PlansFragment;

import java.util.List;

public class PlannedPresenter implements plansPresenterService {


    MealParentReposiatory reposiatory;
    PlannedContract plannedContract;

    public PlannedPresenter(MealParentReposiatory reposiatory, PlannedContract plannedContract) {
        this.reposiatory = reposiatory;
        this.plannedContract = plannedContract;
    }
    public PlannedPresenter(MealParentReposiatory reposiatory) {
        this.reposiatory = reposiatory;
    }


    @Override
    public void deletePlannedMealById(String plannedMealId) {
        reposiatory.deletePlannedMealById(plannedMealId);
    }


    @Override
    public LiveData<List<PlannedMeal>> getAllPlanned() {

        return  reposiatory.getAllPlannedMeals();
    }


    @Override
    public void getAllPlannedMealsWithDate(String day) {
        reposiatory.getMealsPlannedByDate(day);
    }


}
