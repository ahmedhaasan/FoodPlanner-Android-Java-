package com.example.foodplanneritiandroidjava.model.reposatory.local;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
import com.example.foodplanneritiandroidjava.model.reposatory.MealDataBase;

import java.util.List;

public class MealsLocalDataSource implements MealsLocalService {

    private static MealsLocalDataSource instance = null;
    MealDao favoriteDao;
    MealPlannedDao plannedDao ;

    public MealsLocalDataSource(Context context) {
        MealDataBase db = MealDataBase.getInstance(context.getApplicationContext());
        favoriteDao = db.getMealDAO();
        plannedDao = db.mealPlannedDao();
        Log.d("DB_INIT", "FavoriteDao and PlannedDao initialized");


    }

    // make instance of this class
    public static MealsLocalService getInstance(Context context) {
        if (instance == null) {
            instance = new MealsLocalDataSource(context);

        }
        return instance;
    }

    @Override
    public void insertMeal(Meal meal) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                favoriteDao.insertMeal(meal);
            }
        }).start();
    }

    @Override
    public void deleteMeal(Meal meal) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                favoriteDao.deleteMeal(meal);
            }
        }).start();
    }

    @Override
    public void addAllMeals(List<Meal> meals) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                favoriteDao.insertMeals(meals);
            }
        }).start();
    }

    // get meal counts
    public LiveData<Integer> getMealCount() {
        return favoriteDao.getMealCount();
    }

    @Override
    public void deleteAllMeals() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                favoriteDao.deleteAllMeals();
            }
        }).start();

    }

    @Override
    public LiveData<List<Meal>> getAllMeals() {
        return favoriteDao.getAllMeals();
    }


    /****************************************/
    /****************************************/
    /****************************************/

    // implemented from LocalDataService but for planned
    @Override
    public void insertPlannedMeal(PlannedMeal plannedMeal) {
        try {
            new Thread(() -> {
                plannedDao.insertMealPlanned(plannedMeal);
                Log.d("DB_INSERT", "Insertion complete");
            }).start();
        } catch (Exception e) {
            Log.e("DB_INSERT_ERROR", "Error inserting planned meal: " + e.getMessage());
        }
    }



    @Override
    public void deletePlannedMealById(String plannedMealId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                plannedDao.deleteMealPlannedById(plannedMealId);

            }
        }).start();
    }


    @Override
    public LiveData<List<PlannedMeal>> getAllPlannedMeals() {
        return plannedDao.getAllMealsPlanned();
    }

    @Override
    public LiveData<List<PlannedMeal>> getPlannedMealWithDate(String day) {
        return plannedDao.getMealsPlannedByDate(day);
    }

    @Override
    public void deleteAllPlannedMeals() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                plannedDao.deleteAllMealsPlanned();

            }
        }).start();
    }

    @Override
    public void insertAllPlannedMeals(List<PlannedMeal> meals) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                plannedDao.insertAllPlannedMeals(meals);
            }
        }).start();
    }


    public LiveData<Meal> getMealById(String mealId) {
        return favoriteDao.getMealById(mealId);
    }

    }

