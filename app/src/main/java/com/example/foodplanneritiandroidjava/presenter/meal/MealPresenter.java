package com.example.foodplanneritiandroidjava.presenter.meal;

import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.network.NetWorkParent;
import com.example.foodplanneritiandroidjava.view.meal.MealFragment;
import com.example.foodplanneritiandroidjava.view.search.SearchFragment;

import java.util.List;

public class MealPresenter implements MealPresenterService, MealsCallBack {

    MealsRemoteDataSource remoteDataSource;
    MealFragment mealFragment;
    SearchFragment searchFragment ;
    String mealType;
    String mealSourceType; // New field to store the type of the meal source


    public MealPresenter(MealsRemoteDataSource remoteDataSource, SearchFragment searchFragment,String mealSourceType) {
        this.remoteDataSource = remoteDataSource;
        this.searchFragment = searchFragment;
        this.mealSourceType = mealSourceType;

    }
    public MealPresenter(MealsRemoteDataSource remoteDataSource, MealFragment mealFragment, String mealType,String mealSourceType) {
        this.remoteDataSource = remoteDataSource;
        this.mealFragment = mealFragment;
        this.mealType = mealType;
        this.mealSourceType = mealSourceType;
    }

    public void setMealTypeAndSearchMethod(String mealType,String mealSourceType){
        this.mealType = mealType;
        this.mealSourceType = mealSourceType;
    }

    // from  Meal Presenter Service
    // check and get meals by name or by ingrediant
    @Override
    public void getMeals() {
        if (mealSourceType.equals(SomeContstants.CATEGORY)) {
            remoteDataSource.getMealsByCategoryName(this, mealType);
        } else if (mealSourceType.equals(SomeContstants.COUNTRY)) {
            remoteDataSource.getMealsByCountry(this, mealType);
        } else if (mealSourceType.equals(SomeContstants.INGREDIANT)) {
            remoteDataSource.getMealsByIngrediant(this, mealType);
        } else if (mealSourceType.equals(SomeContstants.MEAL_NAME)) {
            remoteDataSource.getMealsByName(this, mealType);
        }else if(mealSourceType.equals(SomeContstants.MEAL_ID)){
            remoteDataSource.getMealsById(this,mealType);
        }else {
            remoteDataSource.makeRandomMealCall(this);
        }
    }





    // from meals\presenter Service
    @Override
    public void onFavClicked(Meal meal) {

    }

    @Override
    public void onAddToPlanClicked(Meal meal) {

    }

    // from mealCall callBack
    @Override
    public void onMealsSuccess(List<Meal> meals) {
        if (mealFragment != null) {
            mealFragment.showMeals(meals);
        }

        if (searchFragment != null) {
            searchFragment.showSearchList(meals);
        }
    }

    @Override
    public void onMealsFailure(String message) {
        if (mealFragment != null) {
            mealFragment.showMealsError(message);
        }

    }
}
