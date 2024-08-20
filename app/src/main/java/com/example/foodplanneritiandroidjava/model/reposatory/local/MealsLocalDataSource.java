package com.example.foodplanneritiandroidjava.model.reposatory.local;

import android.content.Context;

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
    public void deleteAllMeals(List<Meal> meals) {
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


    // implemented from LocalDataService but for planned
    @Override
    public void insertPlannedMeal(PlannedMeal plannedMeal) {
        plannedDao.insertMealPlanned(plannedMeal);
    }

    @Override
    public void deletePlannedMealById(String plannedMealId) {
        plannedDao.deleteMealPlannedById(plannedMealId);
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
        plannedDao.deleteAllMealsPlanned();
    }


    public LiveData<Meal> getMealById(String mealId) {
        return favoriteDao.getMealById(mealId);
    }

    }

