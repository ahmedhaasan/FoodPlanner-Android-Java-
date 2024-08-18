package com.example.foodplanneritiandroidjava.view.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.presenter.meal.MealPresenter;
import com.example.foodplanneritiandroidjava.view.meal.MealAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.checkerframework.checker.units.qual.K;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements SearchContract {

    EditText searchMealField;
    Button searchMealButton;
    ChipGroup chipMealGroup ;
    Chip categoriesChip,countriesChip,ingrediantsChip,mealNameChip,mealIdChip;
    RecyclerView searchRecycler ;
    LinearLayoutManager  searchMealManager ;

    // meal presente instance
    MealPresenter mealPresenter;
    // meal adapter
    MealAdapter mealAdapter;
    // meal list
    List<Meal>  mealList ;
    // remote source
    MealsRemoteDataSource mealsRemoteDataSource ;
    //
    String mealType ;
    String mealSourceType ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // intializatio
        mealList = new ArrayList<>();
        mealAdapter = new MealAdapter(getContext(),mealList);
        mealsRemoteDataSource = MealsRemoteDataSource.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    // onViewCreated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchMealButton = view.findViewById(R.id.searchForMeal_button);
        searchMealField = view.findViewById(R.id.searchForMeal_field);
        chipMealGroup = view.findViewById(R.id.chipGroupFilters);
        categoriesChip = view.findViewById(R.id.category_chip);
        countriesChip = view.findViewById(R.id.countries_chip);
        ingrediantsChip = view.findViewById(R.id.ingrediants_chip);
        mealNameChip = view.findViewById(R.id.meal_name_chip);
        mealIdChip = view.findViewById(R.id.meal_id_chip);
        // recycler
        searchRecycler = view.findViewById(R.id.mealSearchRecycler);
        searchRecycler.setHasFixedSize(true);
        // manager
        searchMealManager = new LinearLayoutManager(getContext());
        searchMealManager.setOrientation(RecyclerView.VERTICAL);
        searchRecycler.setLayoutManager(searchMealManager);
        // set adapter
        searchRecycler.setAdapter(mealAdapter);
        mealPresenter = new MealPresenter(mealsRemoteDataSource,this,mealSourceType);


        /*// Initialize the presenter with the current fragment
        mealPresenter = new MealPresenter(mealsRemoteDataSource, this);*/

        // actions on search button
        // Inside onClick method in SearchFragment
        searchMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealType = searchMealField.getText().toString().trim();
                if (!mealType.isEmpty()) {
                    // If a chip is selected, filter the meals within the current list
                    mealPresenter.setMealTypeAndSearchMethod(mealType,SomeContstants.CATEGORY);
                    mealPresenter.getMeals();
                } else {
                    Toast.makeText(getContext(), "please write anything to search", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    @Override
    public void showSearchList(List<Meal> searchedMeals) {
        if (searchedMeals != null) {
            mealAdapter.setMealList(searchedMeals);
        } else {
            mealAdapter.setMealList(new ArrayList<>());  // Pass an empty list if searchedMeals is null
        }
    }
}