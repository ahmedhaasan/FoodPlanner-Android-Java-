package com.example.foodplanneritiandroidjava.model.reposatory.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.reposatory.MealDataBase;

import java.util.List;

public class MealsLocalDataSource implements MealsLocalService {


    private static MealsLocalDataSource instance = null;
    MealDao favoriteDao;

    private MealsLocalDataSource(Context context) {
        MealDataBase db = MealDataBase.getInstance(context.getApplicationContext());
        favoriteDao = db.getMealDAO();

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
                favoriteDao.addMeals(meals);
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
            favoriteDao.deleteAllMeals(meals);
        }
    }).start();

    }

    @Override
    public LiveData<List<Meal>> getAllMeals() {
       return favoriteDao.getAllMeals();
    }


}
