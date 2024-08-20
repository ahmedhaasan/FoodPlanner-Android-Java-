package com.example.foodplanneritiandroidjava.view.plans;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;

import java.util.List;

public class PlansFragment extends Fragment implements PlannedContract {


    RecyclerView plannedRecyclerView;
    LinearLayoutManager plansManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plans, container, false);
    }

    //

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plannedRecyclerView = view.findViewById(R.id.planned_recycler);
        plannedRecyclerView.setHasFixedSize(true);
        plansManager = new LinearLayoutManager(getContext());
        plansManager.setOrientation(RecyclerView.VERTICAL);

    }


    // implemented from plannedContract

    @Override
    public void showPlannedMeals(LiveData<List<PlannedMeal>> plannedMeals) {

    }

    @Override
    public void showPlannedMealsWithData(LiveData<List<PlannedMeal>> plannedMeals) {

    }

    @Override
    public void showPlannedError(String error) {

    }

    @Override
    public void onPlannedCliced(PlannedMeal plannedMeal) {

    }

    @Override
    public void onPlannedDeleted(PlannedMeal plannedMeal) {

    }

    @Override
    public void onPlannedAddedToFavorite(PlannedMeal plannedMeal) {

    }
}