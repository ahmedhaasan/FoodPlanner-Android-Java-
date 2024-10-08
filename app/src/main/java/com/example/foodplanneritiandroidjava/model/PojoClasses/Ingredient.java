package com.example.foodplanneritiandroidjava.model.PojoClasses;

import com.example.foodplanneritiandroidjava.model.network.Filter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Ingredient implements Serializable, Filter {
    public Ingredient (String name ){
        this.name = name ;
    }

    @SerializedName("idIngredient")
    private String id;
    @SerializedName("strIngredient")
    private String name;
    @SerializedName("strDescription")
    private String description;


    public String getDescription() {
        return description;
    }


    @Override
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }
*/

    // get the image of the ingridiant from the Api
    @Override
    public String getThumb() {
        return "https://www.themealdb.com/images/ingredients/" + getName() + ".png";
    }
}