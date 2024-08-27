package com.example.foodplanneritiandroidjava.view.details;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;

import java.util.List;

public interface DetailsContract {

        void onShowDetails(List<Meal> meal);
        void onMealDetailsFails(String error );
}
