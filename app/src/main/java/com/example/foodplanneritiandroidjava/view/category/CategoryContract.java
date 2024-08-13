package com.example.foodplanneritiandroidjava.view.category;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;

import java.util.List;

public interface CategoryContract {
    void showsCategories(List<Category> categories);
    void showCategoriesError(String message);
}
