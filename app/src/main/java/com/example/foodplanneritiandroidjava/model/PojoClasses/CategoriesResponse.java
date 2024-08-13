package com.example.foodplanneritiandroidjava.model.PojoClasses;

import java.util.List;


/*
Purpose: Represents the response
from the API when fetching a list of meal categories.
It contains a list of Category objects.
 */
public class CategoriesResponse {
    private List<Category> categories;
    // Getters and Setters

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
