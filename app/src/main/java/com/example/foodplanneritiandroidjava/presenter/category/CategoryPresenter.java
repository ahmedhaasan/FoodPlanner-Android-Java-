package com.example.foodplanneritiandroidjava.presenter.category;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.view.HomeFragment;

import java.util.List;

public class CategoryPresenter implements CategoryPresenterService, CategoriesCallBack {

    MealsRemoteDataSource remoteDataSource ;
    HomeFragment homeFragment ;
    List<Category> categories ;

    public CategoryPresenter(MealsRemoteDataSource remoteDataSource, HomeFragment homeFragment,List<Category>categories) {
        this.homeFragment = homeFragment;
        this.remoteDataSource = remoteDataSource;
        this.categories = categories ;
    }

    @Override
    public void getCategories() {

        remoteDataSource.makeCategoriesCall(this);
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
