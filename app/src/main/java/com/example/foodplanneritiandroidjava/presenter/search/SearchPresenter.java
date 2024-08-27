package com.example.foodplanneritiandroidjava.presenter.search;

import android.widget.Toast;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Country;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.CountriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.IngrediantsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.search.SearchContract;

import java.util.List;

public class SearchPresenter  implements  SearchPresenterService,MealsCallBack,
        CategoriesCallBack,CountriesCallBack,IngrediantsCallBack{

    MealParentReposiatory reposiatory ;
    SearchContract searchContract ;

    public SearchPresenter(MealParentReposiatory reposiatory,SearchContract searchContract){
     this.reposiatory = reposiatory;
     this.searchContract = searchContract ;
    }

    @Override
    public void makeCategoryCallBack() {

        reposiatory.makeCategoryCallBack(this);
    }


    @Override
    public void getMealsByCategoryName(String category) {

        reposiatory.getMealsByCategoryName(this,category);
    }

    @Override
    public void getMealsByCategoryID( String id) {

        reposiatory.getMealsByCategoryID(this,id);
    }

    @Override
    public void getMealsByCountry( String country) {
        reposiatory.getMealsByCountry(this,country);

    }

    @Override
    public void getMealsByIngrediant( String ingrediant) {

        reposiatory.getMealsByIngrediant(this,ingrediant);

    }

    @Override
    public void getMealsByName( String name) {

        reposiatory.getMealsByName(this,name);
    }

    @Override
    public void getAllCountries() {
        reposiatory.getAllCountries(this);
    }

    @Override
    public void getIngridiants() {
        reposiatory.getIngridiants(this);

    }




    @Override
    public void getMealsByFristLetter(String fristLetter) {
        reposiatory.getMealsByFristLetter(this,fristLetter);
    }

    @Override
    public void onMealsSuccess(List<Meal> meals) {
        searchContract.onSearchSuccess(meals);
    }

    @Override
    public void onMealsFailure(String message) {
        searchContract.onSearchFail(message);
    }

    @Override
    public void onSuccessCategory(List<Category> categories) {
        searchContract.showSuccessCategories(categories);
    }

    @Override
    public void onFailurResult(String message) {

        searchContract.showFailCatigories(message);
    }

    @Override
    public void onCountriesSuccess(List<Country> countries) {

        searchContract.showSuccessCountries(countries);
    }

    @Override
    public void onCountriesFails(String message) {


        searchContract.showFailCountries(message);
    }

    @Override
    public void onSuccesIngrediants(List<Ingredient> ingredients) {

        searchContract.showSuccessIngrediants(ingredients);
    }

    @Override
    public void onFailIngridiants(String message) {

        searchContract.showFailIngrediants(message);
    }
}
