package com.example.foodplanneritiandroidjava.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.presenter.DailyMealPresenter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements  OnDailyMealShows {


    CardView dailyMealCardView ;
    ImageView dailyMealImage;
    TextView dailyMealName ;

    ///
    List<Meal> randomMeal ;
    DailyMealPresenter presenter ;
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
        dailyMealCardView = view.findViewById(R.id.dailyMealCardView);
        dailyMealImage = view.findViewById(R.id.dailyMealImage);
        dailyMealName = view.findViewById(R.id.dailyMealName);
        randomMeal = new ArrayList<>();
        // intialize presenter
        presenter = new DailyMealPresenter(new MealsRemoteDataSource(),this);
        presenter.geDailyMeal();




    }



    // implemented from
    @Override
    public void showDailyMeals(List<Meal> meals) {
        randomMeal.addAll(meals);
        // this for image
        dailyMealName.setText(randomMeal.get(0).getName());
        Glide.with(this)
                .load(randomMeal.get(0).getThumbnail())
                .apply(new RequestOptions().override(200, 200))
                .into(dailyMealImage);
    }

    @Override
    public void showDailyError(String message) {

    }
}