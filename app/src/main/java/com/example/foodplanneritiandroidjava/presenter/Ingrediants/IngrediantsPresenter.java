package com.example.foodplanneritiandroidjava.presenter.Ingrediants;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.network.IngrediantsCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.Ingrediants.IngrediantsContract;

import java.util.List;

public class IngrediantsPresenter implements  IngrediantPresenterService , IngrediantsCallBack {

    MealParentReposiatory reposiatory;
    IngrediantsContract ingrediantsContract;

    public IngrediantsPresenter(MealParentReposiatory reposiatory, IngrediantsContract ingrediantsContract) {
        this.reposiatory = reposiatory;
        this.ingrediantsContract = ingrediantsContract;
    }

    @Override
    public void getIngrediants() {
        reposiatory.getIngridiants(this);
    }

    @Override
    public void onSuccesIngrediants(List<Ingredient> ingredients) {
        ingrediantsContract.showsIngrediants(ingredients);
    }

    @Override
    public void onFailIngridiants(String message) {

        ingrediantsContract.showsIngrediantsError(message);
    }
}
