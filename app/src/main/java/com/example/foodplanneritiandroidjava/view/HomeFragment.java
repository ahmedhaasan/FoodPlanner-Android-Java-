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

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;

import java.util.List;

public class HomeFragment extends Fragment implements MealsCallBack {


    CardView dailyMealCardView ;
    ImageView dailyMealImage;
    TextView dailyMealName ;
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
    }


    // implemented methods from MealsCall Back InterFace
    @Override
    public void onMealsSuccess(List<Meal> meals) {

    }

    @Override
    public void onMealsFailure(String message) {

    }
}