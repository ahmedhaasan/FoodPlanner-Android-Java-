package com.example.foodplanneritiandroidjava.presenter.Ingrediants;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.network.IngrediantsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.view.home.HomeFragment;

import java.util.List;

public class IngrediantsPresenter implements  IngrediantPresenterService , IngrediantsCallBack {

    MealsRemoteDataSource remoteDataSource ;
    HomeFragment homeFragment ;

    public IngrediantsPresenter(MealsRemoteDataSource remoteDataSource, HomeFragment homeFragment) {
        this.remoteDataSource = remoteDataSource;
        this.homeFragment = homeFragment;
    }

    @Override
    public void getIngrediants() {
        remoteDataSource.getIngridiants(this);
    }

    @Override
    public void onSuccesIngrediants(List<Ingredient> ingredients) {
        homeFragment.showsIngrediants(ingredients);
    }

    @Override
    public void onFailIngridiants(String message) {

        homeFragment.showsIngrediantsError(message);
    }
}
