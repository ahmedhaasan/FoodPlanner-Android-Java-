package com.example.foodplanneritiandroidjava.view.favorite;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

public interface FavoriteContract {

    void onFavoriteClicked(Meal meal);
    void onFavoriteDeleted(Meal meal);
}
