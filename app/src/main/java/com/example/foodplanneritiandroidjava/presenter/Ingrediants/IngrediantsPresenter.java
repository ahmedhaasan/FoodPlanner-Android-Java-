package com.example.foodplanneritiandroidjava.presenter.Ingrediants;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.network.IngrediantsCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.home.homeActivity.HomeFragment;

import java.util.List;

public class IngrediantsPresenter implements  IngrediantPresenterService , IngrediantsCallBack {

    MealParentReposiatory reposiatory;
    HomeFragment homeFragment ;

    public IngrediantsPresenter(MealParentReposiatory reposiatory, HomeFragment homeFragment) {
        this.reposiatory = reposiatory;
        this.homeFragment = homeFragment;
    }

    @Override
    public void getIngrediants() {
        reposiatory.getIngridiants(this);
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
