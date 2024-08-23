package com.example.foodplanneritiandroidjava.view.search.presenter;

import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.CountriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.IngrediantsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;

public interface SearchPresenterService {

    public void makeCategoryCallBack(CategoriesCallBack callBack) ;
    public void makeRandomMealCall(MealsCallBack mealsCallBack) ;
    public void getMealsByCategoryName(MealsCallBack callBack, String category);
    public void getMealsByCategoryID(MealsCallBack callBack, String id);
    public void getMealsByCountry(MealsCallBack callBack, String country) ;
    public void getMealsByIngrediant(MealsCallBack callBack, String ingrediant);
    public void getMealsByName(MealsCallBack callBack, String name) ;
    public void getAllCountries(CountriesCallBack callBack) ;
    public void getIngridiants(IngrediantsCallBack callBack) ;
    public void getMealsByFristLetter(MealsCallBack callBack, String fristLetter) ;

    }

