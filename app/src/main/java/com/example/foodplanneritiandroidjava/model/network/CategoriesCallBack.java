package com.example.foodplanneritiandroidjava.model.network;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;

import java.util.List;

public interface CategoriesCallBack {
    void onSuccessCategory(List<Category> categories );
    void onFailurResult (String message);
}
