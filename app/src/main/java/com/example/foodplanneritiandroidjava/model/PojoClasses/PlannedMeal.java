package com.example.foodplanneritiandroidjava.model.PojoClasses;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_planned")
public class PlannedMeal {

    @PrimaryKey
    @NonNull

    private String id;
    private String name;
    private String category;
    private String country;
    private String thumb;
    private String day;

    public PlannedMeal(String id, String name, String category, String country, String thumb, String day) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.country = country;
        this.thumb = thumb;
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
