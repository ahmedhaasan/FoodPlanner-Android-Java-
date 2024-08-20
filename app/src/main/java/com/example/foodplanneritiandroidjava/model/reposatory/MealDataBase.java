package com.example.foodplanneritiandroidjava.model.reposatory;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealDao;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealPlannedDao;

// Create database for meal
// Will make this class singleton
@Database(entities = {Meal.class, PlannedMeal.class}, version = 2)
public abstract class MealDataBase extends RoomDatabase {

    private static volatile MealDataBase instance;

    public abstract MealPlannedDao mealPlannedDao();
    public abstract MealDao getMealDAO();

    // Migration from version 1 to 2
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(SupportSQLiteDatabase database) {
                // Create the new table 'meal_planned'
                database.execSQL("CREATE TABLE IF NOT EXISTS `meal_planned` (" +
                        "`id` TEXT NOT NULL PRIMARY KEY, " +
                        "`name` TEXT, " +
                        "`category` TEXT, " +
                        "`country` TEXT, " +
                        "`thumb` TEXT, " +
                        "`day` TEXT)");
            }
        };

    // Creating an instance of database
    public static synchronized MealDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MealDataBase.class, "Meal_DataBase")
                    .addMigrations(MIGRATION_1_2) // Add migration here
                    .fallbackToDestructiveMigration() // Optional: handle cases where migration is not found
                    .build();
        }
        return instance;
    }
}
