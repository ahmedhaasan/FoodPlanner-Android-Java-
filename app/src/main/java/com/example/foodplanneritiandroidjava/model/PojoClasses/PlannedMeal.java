package com.example.foodplanneritiandroidjava.model.PojoClasses;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_planned")
public class PlannedMeal implements Parcelable {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String category;
    private String country;
    private String thumb;
    private String day;
    private String date ;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PlannedMeal() {
    }
    // Constructor
    public PlannedMeal(String id, String name, String category, String country, String thumb, String day,String date) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.country = country;
        this.thumb = thumb;
        this.day = day;
        this.date = date ;
    }

    // Parcelable constructor
    protected PlannedMeal(Parcel in) {
        id = in.readString();
        name = in.readString();
        category = in.readString();
        country = in.readString();
        thumb = in.readString();
        day = in.readString();
    }

    // Getters and setters
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeString(country);
        parcel.writeString(thumb);
        parcel.writeString(day);
    }

    public static final Creator<PlannedMeal> CREATOR = new Creator<PlannedMeal>() {
        @Override
        public PlannedMeal createFromParcel(Parcel in) {
            return new PlannedMeal(in);
        }

        @Override
        public PlannedMeal[] newArray(int size) {
            return new PlannedMeal[size];
        }
    };
}
