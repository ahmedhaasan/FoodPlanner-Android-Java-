package com.example.foodplanneritiandroidjava.view.meal;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.presenter.meal.MealPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MealFragment extends Fragment implements MealsContract {

    TextView mealTypeParent;
    Context context;
    RecyclerView mealRecycler;
    String mealType,mealSourceType ;
    LinearLayoutManager mealManger;


    // List of meals
    List<Meal> mealList;

    // Meal Presenter and remote source and Adapter
    MealParentReposiatory reposiatory;
    MealPresenter mealPresenter;
    MealAdapter mealAdapter;

    // Default no-argument constructor (required for Fragment)
    public MealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context; // Assign the context here
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check the navigated type
        if (getArguments() != null) {
            MealFragmentArgs args = MealFragmentArgs.fromBundle(getArguments());
            mealType = args.getMealType();
           mealSourceType = args.getMealSourceType();// This will tell you if it's category, country, or ingredient

        }

        // Initialize the meal List
        mealList = new ArrayList<>();

        // Initialize data source
        reposiatory = MealParentReposiatory.getInstance(new MealsRemoteDataSource(),new MealsLocalDataSource(getContext()));

        // Initialize meal presenter
        mealPresenter = new MealPresenter(reposiatory, this, mealType,mealSourceType);
        mealPresenter.getMeals();

        // Initialize meal adapter
        mealAdapter = new MealAdapter(getContext(), mealList,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealTypeParent = view.findViewById(R.id.category_name);
        mealTypeParent.setText(mealType);
        mealRecycler = view.findViewById(R.id.mealsRecycler);
        mealRecycler.setHasFixedSize(true);
        mealManger = new LinearLayoutManager(context);
        mealManger.setOrientation(RecyclerView.VERTICAL);
        mealRecycler.setLayoutManager(mealManger);
        mealRecycler.setAdapter(mealAdapter);

    }

    @Override
    public void showMeals(List<Meal> meals) {
        mealAdapter.setMealList(meals);
    }

    @Override
    public void showMealsError(String message) {
        Toast.makeText(context, "error getting meals: " + message, Toast.LENGTH_SHORT).show();
    }
}
