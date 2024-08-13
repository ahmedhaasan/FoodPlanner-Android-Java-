package com.example.foodplanneritiandroidjava.network.PojoClasses;

import com.google.gson.annotations.SerializedName;

// this class represent a single meal

/*
Take care of this
: filter.php - Used to get meals by category (getMealsByCategory method).
search.php - Used to search for meals by name (searchMealsByName method).
lookup.php - Used to get meal details by ID (getMealById method).
random.php - Used to get a random meal (getRandomMeal method).
 */
public class Meal {
    @SerializedName("idMeal")
    private String id;
    @SerializedName("strMeal")
    private String name;
    @SerializedName("strMealThumb")
    private String thumbnail;
    // getters and setters

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
