package com.example.foodplanneritiandroidjava.presenter.category;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.category.CategoryContract;

import java.util.List;

public class CategoryPresenter implements CategoryPresenterService, CategoriesCallBack {

    MealParentReposiatory reposiatory;
    CategoryContract categoryContract;   // this from category contract which is implemented in home fragment


    public CategoryPresenter(MealParentReposiatory reposiatory, CategoryContract categoryContract) {
        this.categoryContract = categoryContract;
        this.reposiatory = reposiatory;

    }

    @Override
    public void getCategories() {

        reposiatory.makeCategoryCallBack(this);
    }

    @Override
    public void onSuccessCategory(List<Category> categories) {
        categoryContract.showsCategories(categories);
    }

    @Override
    public void onFailurResult(String message) {

        categoryContract.showCategoriesError(message);
    }
}
