package com.example.foodplanneritiandroidjava.view.favorite;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

public interface FavoriteContract {

    void onFavoriteDeleted(Meal meal);
    void onImageClicked(String imageId);
}
