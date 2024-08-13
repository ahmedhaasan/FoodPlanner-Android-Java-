package com.example.foodplanneritiandroidjava.model.PojoClasses;

import com.google.gson.annotations.SerializedName;


// this class represent a Single category
public class Category {
    @SerializedName("idCategory")
    private String id;
    @SerializedName("strCategory")
    private String name;
    @SerializedName("strCategoryThumb")
    private String thumbnail;
    @SerializedName("strCategoryDescription")
    private String description;
    // getters and setters


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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}