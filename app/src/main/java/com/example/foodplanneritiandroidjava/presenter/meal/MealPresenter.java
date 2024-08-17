package com.example.foodplanneritiandroidjava.presenter.meal;

import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.network.NetWorkParent;
import com.example.foodplanneritiandroidjava.view.meal.MealFragment;

import java.util.List;

public class MealPresenter implements MealPresenterService, MealsCallBack {

    MealsRemoteDataSource remoteDataSource;
    MealFragment mealFragment;
    String mealType;
    String mealSourceType; // New field to store the type of the meal source


    public MealPresenter(MealsRemoteDataSource remoteDataSource, MealFragment mealFragment, String mealType,String mealSourceType) {
        this.remoteDataSource = remoteDataSource;
        this.mealFragment = mealFragment;
        this.mealType = mealType;
        this.mealSourceType = mealSourceType;
    }


    // from  Meal Presenter Service
    // check and get meals by name or by ingrediant
    @Override
    public void getMeals() {
        if (mealSourceType == SomeContstants.CATEGORY){
            remoteDataSource.getMealsByCategoryName(this, mealType);

        } else if (mealSourceType == SomeContstants.COUNTRY) {
         remoteDataSource.getMealsByCountry(this,mealType);
        } else if (mealSourceType.equals(SomeContstants.INGREDIANT)) {
            remoteDataSource.getMealsByIngrediant(this,mealType);
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

        mealFragment.showMeals(meals);
    }

    @Override
    public void onMealsFailure(String message) {

        mealFragment.showMealsError(message);
    }
}
