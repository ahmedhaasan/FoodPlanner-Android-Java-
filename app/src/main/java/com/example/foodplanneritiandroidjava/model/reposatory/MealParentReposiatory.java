package com.example.foodplanneritiandroidjava.model.reposatory;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.CountriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.IngrediantsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;

import java.util.Collections;
import java.util.List;

public class MealParentReposiatory implements MealParentReposiatoryService {

    MealsRemoteDataSource remoteDataSource;
    MealsLocalDataSource localDataSource;

    public static MealParentReposiatory repo = null;

    public MealParentReposiatory(MealsRemoteDataSource remoteDataSource, MealsLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static MealParentReposiatory getInstance(MealsRemoteDataSource remoteDataSource, MealsLocalDataSource localDataSource) {
        if (repo == null) {
            repo = new MealParentReposiatory(remoteDataSource, localDataSource);

        }
        return repo;
    }

    @Override
    public void insertmeal(Meal meal) {
        localDataSource.insertMeal(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {

        localDataSource.deleteMeal(meal);
    }

    @Override
    public void addAllLocalMeals(List<Meal> meals) {

        localDataSource.addAllMeals(meals);
    }

    @Override
    public void deleteAllLocalMeals(List<Meal> meals) {

        localDataSource.deleteAllMeals(meals);
    }

    // return all local meals
    @Override
    public LiveData<List<Meal>> getAllLocalMealsMeals() {
        return localDataSource.getAllMeals();
    }

    // remote functions
    @Override
    public void makeCategoryCallBack(CategoriesCallBack callBack) {

        remoteDataSource.makeCategoriesCall(callBack);
    }

    @Override
    public void makeRandomMealCall(MealsCallBack mealsCallBack) {

        remoteDataSource.makeRandomMealCall(mealsCallBack);
    }

    @Override
    public void getMealsByCategoryName(MealsCallBack callBack, String category) {
        remoteDataSource.getMealsByCategoryName(callBack, category);
    }

    @Override
    public void getMealsByCategoryID(MealsCallBack callBack, String id) {

        remoteDataSource.getMealsByCategoryID(callBack, id);
    }

    @Override
    public void getMealsByCountry(MealsCallBack callBack, String country) {

        remoteDataSource.getMealsByCountry(callBack, country);
    }

    @Override
    public void getMealsByIngrediant(MealsCallBack callBack, String ingrediant) {

        remoteDataSource.getMealsByIngrediant(callBack, ingrediant);
    }

    @Override
    public void getMealsByName(MealsCallBack callBack, String name) {

        remoteDataSource.getMealsByName(callBack, name);
    }

    @Override
    public void getAllCountries(CountriesCallBack callBack) {

        remoteDataSource.getAllCountries(callBack);
    }

    @Override
    public void getIngridiants(IngrediantsCallBack callBack) {

        remoteDataSource.getIngridiants(callBack);
    }

    @Override
    public void getMealsById(MealsCallBack callBack, String id) {

        remoteDataSource.getMealsById(callBack, id);
    }

 /*   @Override
    public List<Meal> getAllMeals() {
        return remoteDataSource.getAllMeals();
    }*/

    @Override
    public void getMealsByFristLetter(MealsCallBack callBack, String fristLetter) {
        remoteDataSource.getMealsByFristLetter(callBack,fristLetter);
    }
}
