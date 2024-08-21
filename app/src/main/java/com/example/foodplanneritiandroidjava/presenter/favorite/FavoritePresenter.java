package com.example.foodplanneritiandroidjava.presenter.favorite;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;

import java.util.List;



public class FavoritePresenter implements  favoritePresenterService{

    MealParentReposiatory reposiatory ;
    private LifecycleOwner lifecycleOwner;

    public FavoritePresenter(LifecycleOwner lifecycleOwner, MealParentReposiatory reposiatory) {
        this.lifecycleOwner = lifecycleOwner;
        this.reposiatory = reposiatory;
    }

    public FavoritePresenter(MealParentReposiatory reposiatory) {
        this.reposiatory = reposiatory ;
    }

    @Override
    public void insertMeal(Meal meal) {
        reposiatory.insertmeal(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {

        reposiatory.deleteMeal(meal);
    }

    @Override
    public void addAllMeals(List<Meal> meals) {

        reposiatory.addAllLocalMeals(meals);
    }

    @Override
    public void deleteAllMeals(List<Meal> meals) {

        reposiatory.deleteAllLocalMeals(meals);
    }

    @Override
    public LiveData<List<Meal>> getAllMeals() {
        return reposiatory.getAllLocalMealsMeals();
    }

    public LiveData<Meal> checkIfMealIsFavorite(String mealId) {
        return reposiatory.getMealById(mealId);
    }

    // temp function
   public LiveData<List<PlannedMeal>> getPlanned( ){
        return reposiatory.getAllPlannedMeals();

    }

}
