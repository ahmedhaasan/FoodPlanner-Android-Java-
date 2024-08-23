package com.example.foodplanneritiandroidjava.view.search;

import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.CountriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.IngrediantsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;

public class SearchPresenter  implements  SearchPresenterService{

    MealParentReposiatory reposiatory ;

    public SearchPresenter(MealParentReposiatory reposiatory){
     this.reposiatory = reposiatory;
    }

    @Override
    public void makeCategoryCallBack(CategoriesCallBack callBack) {

        reposiatory.makeCategoryCallBack(callBack);
    }

    @Override
    public void makeRandomMealCall(MealsCallBack mealsCallBack) {

        reposiatory.makeRandomMealCall(mealsCallBack);
    }

    @Override
    public void getMealsByCategoryName(MealsCallBack callBack, String category) {

        reposiatory.getMealsByCategoryName(callBack,category);
    }

    @Override
    public void getMealsByCategoryID(MealsCallBack callBack, String id) {

        reposiatory.getMealsByCategoryID(callBack,id);
    }

    @Override
    public void getMealsByCountry(MealsCallBack callBack, String country) {
        reposiatory.getMealsByCountry(callBack,country);

    }

    @Override
    public void getMealsByIngrediant(MealsCallBack callBack, String ingrediant) {

        reposiatory.getMealsByIngrediant(callBack,ingrediant);

    }

    @Override
    public void getMealsByName(MealsCallBack callBack, String name) {

        reposiatory.getMealsByName(callBack,name);
    }

    @Override
    public void getAllCountries(CountriesCallBack callBack) {
        reposiatory.getAllCountries(callBack);
    }

    @Override
    public void getIngridiants(IngrediantsCallBack callBack) {
        reposiatory.getIngridiants(callBack);

    }


    @Override
    public void getMealsByFristLetter(MealsCallBack callBack, String fristLetter) {
    reposiatory.getMealsByFristLetter(callBack,fristLetter);
    }
}
