package com.example.foodplanneritiandroidjava.model.reposatory.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

import java.util.List;

@Dao
public interface MealDao {
    // Get all meals
    @Query("SELECT * FROM meals")
    LiveData<List<Meal>> getAllMeals();

    // Insert a specific meal
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    // Delete a specific meal
    @Delete
    void deleteMeal(Meal meal);

    // Delete all meals
    @Query("DELETE FROM meals")
    void deleteAllMeals();

    // Return meal count
    @Query("SELECT COUNT(*) FROM meals")
    LiveData<Integer> getMealCount();

    // Insert or replace all meals
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<Meal> meals);

    @Query("SELECT * FROM meals WHERE id = :mealId")
    LiveData<Meal> getMealById(String mealId);
    }


