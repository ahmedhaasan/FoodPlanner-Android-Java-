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
    // get all meals
    @Query("SELECT * FROM meals")
    LiveData<List<Meal>> getAllMeals();
    // insert all meals
    @Query("DELETE FROM meals")
    void deleteAllMeals(List<Meal> meals);
    // insert a specific meal
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal); // insert meal

    // delete a specific meal
    @Delete
    void deleteMeal(Meal meal);

    // return meal count
    @Query("SELECT COUNT(*) FROM meals")
    LiveData<Integer> getMealCount();

    // add all meals
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMeals(List<Meal> meals);





}
