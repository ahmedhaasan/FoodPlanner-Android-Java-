package com.example.foodplanneritiandroidjava.model.reposatory;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealDao;

// create data base for meal
// will make this class singlton
@Database(entities = {Meal.class}, version = 1)

public abstract class MealDataBase extends RoomDatabase {

    private static MealDataBase instance = null;
    public abstract MealDao getMealDAO();
    // creating an instance of database
    public static synchronized  MealDataBase getInstance(Context context){
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),MealDataBase.class,"Meal_DataBase").build();
        }
        return  instance ;
    }


}
