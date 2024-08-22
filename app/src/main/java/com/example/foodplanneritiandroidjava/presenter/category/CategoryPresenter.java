package com.example.foodplanneritiandroidjava.presenter.category;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.home.homeActivity.HomeFragment;

import java.util.List;

public class CategoryPresenter implements CategoryPresenterService, CategoriesCallBack {

    MealParentReposiatory reposiatory;
    HomeFragment homeFragment ;


    public CategoryPresenter(MealParentReposiatory reposiatory, HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        this.reposiatory = reposiatory;

    }

    @Override
    public void getCategories() {

        reposiatory.makeCategoryCallBack(this);
    }

    @Override
    public void onSuccessCategory(List<Category> categories) {
        homeFragment.showsCategories(categories);
    }

    @Override
    public void onFailurResult(String message) {

        homeFragment.showCategoriesError(message);
    }
}
