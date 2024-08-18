package com.example.foodplanneritiandroidjava.view.search;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

import java.util.List;

public interface SearchContract {

    void showSearchList(List<Meal> searchedMeals);
}
