package com.example.foodplanneritiandroidjava.presenter.search;

import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.CountriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.IngrediantsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;

public interface SearchPresenterService {

    public void makeCategoryCallBack() ;
    public void getMealsByCategoryName( String category);
    public void getMealsByCategoryID(String id);
    public void getMealsByCountry( String country) ;
    public void getMealsByIngrediant( String ingrediant);
    public void getMealsByName( String name) ;
    public void getAllCountries() ;
    public void getIngridiants() ;
    void getMealsByFristLetter(String fristLetter);

    }

