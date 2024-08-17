package com.example.foodplanneritiandroidjava.presenter.meal;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.view.meal.MealFragment;

import java.util.List;

public class MealPresenter implements MealPresenterService, MealsCallBack {

    MealsRemoteDataSource remoteDataSource ;
    MealFragment mealFragment ;
    String mealType ;

    public MealPresenter(MealsRemoteDataSource remoteDataSource, MealFragment mealFragment,String mealType) {
        this.remoteDataSource = remoteDataSource;
        this.mealFragment = mealFragment;
        this.mealType = mealType;
    }

    @Override
    public void getMeals() {
        remoteDataSource.getMealsByCategoryName(this,mealType);
    }

    @Override
    public void onFavClicked(Meal meal) {

    }

    @Override
    public void onAddToPlanClicked(Meal meal) {

    }

    @Override
    public void onMealsSuccess(List<Meal> meals) {

    }

    @Override
    public void onMealsFailure(String message) {

    }
}
