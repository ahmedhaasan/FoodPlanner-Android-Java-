package com.example.foodplanneritiandroidjava.view.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.presenter.favorite.FavoritePresenter;
import com.example.foodplanneritiandroidjava.view.meal.MealAdapter;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment  implements FavoriteContract{


    RecyclerView favoriteRecyclerView ;
    MealAdapter mealAdapter ;
    List<Meal> favMeals ;
    FavoritePresenter favoritePresenter ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favMeals = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // intialization
        mealAdapter = new MealAdapter(getContext(),favMeals,this);
        favoriteRecyclerView = view.findViewById(R.id.favorite_recycler);
        LinearLayoutManager favManager = new LinearLayoutManager(getContext());
        favManager.setOrientation(RecyclerView.VERTICAL);
        favoriteRecyclerView.setLayoutManager(favManager);
        favoriteRecyclerView.setHasFixedSize(true);
        favoriteRecyclerView.setAdapter(mealAdapter);

        // intialize the favorite presenter
        favoritePresenter = new FavoritePresenter(new MealParentReposiatory(new MealsRemoteDataSource(),new MealsLocalDataSource(getContext())));
        LiveData<List<Meal>> favMeals = favoritePresenter.getAllMeals();
        favMeals.observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                mealAdapter.setMealList(meals);

            }
        });

    }

    @Override
    public void onFavoriteClicked(Meal meal) {

    }

    @Override
    public void onFavoriteDeleted(Meal meal) {

    }
}