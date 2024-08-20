package com.example.foodplanneritiandroidjava.model.reposatory.local;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;

import java.util.List;

@Dao
public interface MealPlannedDao {



        @Insert
        void insertMealPlanned(PlannedMeal mealPlanned);

        @Query("SELECT * FROM meal_planned")
        LiveData<List<PlannedMeal>> getAllMealsPlanned();

        @Query("SELECT * FROM meal_planned WHERE day = :date")
        LiveData<List<PlannedMeal>> getMealsPlannedByDate(String date);

        @Query("DELETE FROM meal_planned WHERE id = :id")
        void deleteMealPlannedById(String id);


        @Query("DELETE FROM meal_planned")
        void deleteAllMealsPlanned();

    }


