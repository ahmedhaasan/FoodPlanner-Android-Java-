package com.example.foodplanneritiandroidjava.presenter.meal;

import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.meal.MealFragment;
import com.example.foodplanneritiandroidjava.view.meal.MealsContract;

import java.util.List;

public class MealPresenter implements MealPresenterService, MealsCallBack {

    MealParentReposiatory reposiatory;
    MealsContract mealsContract;
    String mealType;
    String mealSourceType; // New field to store the type of the meal source


    public MealPresenter(MealParentReposiatory reposiatory, MealsContract mealsContract) {
        this.reposiatory = reposiatory;
        this.mealsContract = mealsContract;
        this.mealType = mealType;
        this.mealSourceType = mealSourceType;
    }



    @Override
    public void getMealsByFristLetter(String fristLetter) {
        reposiatory.getMealsByFristLetter(this,fristLetter);
    }


    // from meals\presenter Service
    @Override
    public void onFavClicked(Meal meal) {

        reposiatory.insertmeal(meal);
    }

    @Override
    public void getMealsByCategoryName(String categoryName) {
        reposiatory.getMealsByCategoryName(this,categoryName);
    }

    @Override
    public void getMealsByCountryName(String countryName) {

        reposiatory.getMealsByCountry(this,countryName);
    }

    @Override
    public void getMealsByIngrediantsName(String ingrediantName) {

        reposiatory.getMealsByIngrediant(this,ingrediantName);
    }

    @Override
    public void getMealsByMealName(String mealName) {

        reposiatory.getMealsByName(this,mealName);
    }

    @Override
    public void getMealsByID(String mealID) {

        reposiatory.getMealsById(this,mealID);
    }

    @Override
    public void getRandomMeal() {

        reposiatory.makeRandomMealCall(this);
    }


    // from mealCall callBack
    @Override
    public void onMealsSuccess(List<Meal> meals) {
        if (mealsContract != null) {
            mealsContract.showMeals(meals);
        }

    }

    @Override
    public void onMealsFailure(String message) {
        if (mealsContract != null) {
            mealsContract.showMealsError(message);
        }

    }
}
