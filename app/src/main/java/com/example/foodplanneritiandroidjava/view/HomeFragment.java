package com.example.foodplanneritiandroidjava.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.presenter.category.CategoryPresenter;
import com.example.foodplanneritiandroidjava.presenter.dailyMeal.DailyMealPresenter;
import com.example.foodplanneritiandroidjava.view.category.CategoryAdapter;
import com.example.foodplanneritiandroidjava.view.category.CategoryContract;
import com.example.foodplanneritiandroidjava.view.dailyMeals.OnDailyMealShows;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnDailyMealShows, CategoryContract {


    CardView dailyMealCardView ;
    ImageView dailyMealImage;
    TextView dailyMealName ;
    RecyclerView categoryRecycler;
    LinearLayoutManager layoutManager;

    ///
    List<Meal> randomMeal ;
    List<Category> categories;
    DailyMealPresenter dailyPresenter;
    CategoryPresenter categoryPresenter ;
    CategoryAdapter categoryAdapter ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize views
        dailyMealCardView = view.findViewById(R.id.dailyMealCardView);
        dailyMealImage = view.findViewById(R.id.dailyMealImage);
        dailyMealName = view.findViewById(R.id.dailyMealName);

        // Initialize RecyclerView and set layout manager
        categoryRecycler = view.findViewById(R.id.categoryRecycler1);
        categoryRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecycler.setLayoutManager(layoutManager);


        // Initialize data lists
        randomMeal = new ArrayList<>();
        categories = new ArrayList<>();

        // Initialize adapter and set it to RecyclerView
        categoryAdapter = new CategoryAdapter(getContext(),categories);
        categoryRecycler.setAdapter(categoryAdapter);

        // Initialize presenters
        dailyPresenter = new DailyMealPresenter(new MealsRemoteDataSource(), this);
        dailyPresenter.geDailyMeal();

        categoryPresenter = new CategoryPresenter(new MealsRemoteDataSource(), this, categories);
        categoryPresenter.getCategories();

    }


    private void updateMealCard(List<Meal> meal) {
        dailyMealName.setText(meal.get(0).getName());
        // Load image using Glide
        Glide.with(this)
                .load(meal.get(0).getThumb())
                .into(dailyMealImage);
    }

    // implemented from
    @Override
    public void showDailyMeals(List<Meal> meals) {
        randomMeal.addAll(meals);
     updateMealCard(meals);
    }

    @Override
    public void showDailyError(String message) {

    }

    // those methods implementd from Category Contract
    @Override
    public void showsCategories(List<Category> categories) {

        categoryAdapter.setCategoryList(categories);
    }

    @Override
    public void showCategoriesError(String message) {

    }
}