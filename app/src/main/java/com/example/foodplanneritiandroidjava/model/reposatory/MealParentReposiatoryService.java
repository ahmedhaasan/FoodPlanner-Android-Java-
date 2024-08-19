package com.example.foodplanneritiandroidjava.model.reposatory;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.CountriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.IngrediantsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;

import java.util.List;

public interface MealParentReposiatoryService {

    public void insertmeal(Meal meal);
    void deleteMeal(Meal meal);
    void addAllLocalMeals(List<Meal> meals);
    void deleteAllLocalMeals(List<Meal> meals);
    LiveData<List<Meal>> getAllLocalMealsMeals();

    // remote
    void makeCategoryCallBack(CategoriesCallBack callBack);
    public void makeRandomMealCall(MealsCallBack mealsCallBack);
    public void getMealsByCategoryName(MealsCallBack callBack, String category) ;
    public void getMealsByCategoryID(MealsCallBack callBack, String id) ;
    public void getMealsByCountry(MealsCallBack callBack, String country) ;
    public void getMealsByIngrediant(MealsCallBack callBack, String ingrediant) ;
    public void getMealsByName(MealsCallBack callBack, String name) ;
    public void getAllCountries(CountriesCallBack callBack) ;
    public void getIngridiants(IngrediantsCallBack callBack) ;
    public void getMealsById(MealsCallBack callBack, String id) ;
/*
    List<Meal>getAllMeals();
*/
    void getMealsByFristLetter(MealsCallBack callBack,String fristLetter);

    }
