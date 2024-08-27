package com.example.foodplanneritiandroidjava.presenter.mealDetails;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.details.DetailsContract;

import java.util.List;

public class DetailsPresenter implements MealDetailsServfice  , MealsCallBack {

    MealParentReposiatory reposiatory ;
    DetailsContract detailsContract;


    public DetailsPresenter(MealParentReposiatory reposiatory, DetailsContract detailsContract) {
        this.reposiatory = reposiatory;
        this.detailsContract = detailsContract;
    }

    @Override
    public void getMealDetail(String id) {
        reposiatory.getMealsById(this,id);
    }


    @Override
    public void onMealsSuccess(List<Meal> meals) {
        detailsContract.onShowDetails(meals);
    }

    @Override
    public void onMealsFailure(String message) {

        detailsContract.onMealDetailsFails(message);
    }

    // handel when user press on method to add to db;
    @Override
    public void insertMealIntoPlanned(PlannedMeal plannedMeal) {

        reposiatory.insertPlannedMeal(plannedMeal);
    }

    @Override
    public void insertMealIntoFavorite(Meal meal) {
        reposiatory.insertmeal(meal);
    }
}
