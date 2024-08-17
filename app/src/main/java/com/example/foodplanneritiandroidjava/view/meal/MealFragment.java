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

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.presenter.meal.MealPresenter;

import java.util.ArrayList;
import java.util.List;


public class MealFragment extends Fragment implements MealsContract{

    Context context ;
    RecyclerView mealRecycler ;
    String mealType ;
    LinearLayoutManager mealManger ;

    // list of meals
    List<Meal> mealList ;

    // meal Presenter and remote sourc  and Adapter
    MealsRemoteDataSource remoteDataSource;
    MealPresenter mealPresenter ;
    MealAdapter mealAdapter ;


    public MealFragment(Context context, String mealType ) {
        this.context = context;
        this.mealType = mealType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // intialize the meal List ;
        mealList = new ArrayList<>();
        remoteDataSource = MealsRemoteDataSource.getInstance();
        mealPresenter = new MealPresenter(remoteDataSource,this,mealType) ;
        mealAdapter = new MealAdapter(getContext(),mealList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }
    // work on OnViewCreated


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealRecycler = view.findViewById(R.id.mealsRecycler);
        mealRecycler.setHasFixedSize(true);
        mealManger = new LinearLayoutManager(context);
        mealManger.setOrientation(RecyclerView.VERTICAL);
        mealRecycler.setLayoutManager(mealManger);

    }

    @Override
    public void showMeals(List<Meal> meals) {

    }

    @Override
    public void showMealsError(String message) {

    }
}