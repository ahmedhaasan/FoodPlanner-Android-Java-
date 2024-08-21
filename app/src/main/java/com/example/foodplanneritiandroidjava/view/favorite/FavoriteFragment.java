package com.example.foodplanneritiandroidjava.view.favorite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.presenter.favorite.FavoritePresenter;
import com.example.foodplanneritiandroidjava.view.home.HomeFragmentDirections;
import com.example.foodplanneritiandroidjava.view.meal.MealAdapter;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment  implements FavoriteContract{

    private FirebaseAuth auth ;
    //
    RecyclerView favoriteRecyclerView ;
    FavoriteAdapter favoriteAdapter ;
    List<Meal> favMeals ;
    FavoritePresenter favoritePresenter ;
    View navView ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favMeals = new ArrayList<>();

        auth = FirebaseAuth.getInstance() ;
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

        navView = view ;
        ////////////////
        // intialization
        favoriteAdapter = new FavoriteAdapter(this,favMeals,getContext());
        favoriteRecyclerView = view.findViewById(R.id.favorite_recycler);
        LinearLayoutManager favManager = new LinearLayoutManager(getContext());
        favManager.setOrientation(RecyclerView.VERTICAL);
        favoriteRecyclerView.setLayoutManager(favManager);
        favoriteRecyclerView.setHasFixedSize(true);
        favoriteRecyclerView.setAdapter(favoriteAdapter);

        // intialize the favorite presenter
        favoritePresenter = new FavoritePresenter(new MealParentReposiatory(new MealsRemoteDataSource(),new MealsLocalDataSource(getContext())));
        LiveData<List<Meal>> favMeals = favoritePresenter.getAllMeals();
        favMeals.observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                favoriteAdapter.setMealList(meals);

            }
        });

    }

    @Override
    public void onFavoriteClicked(Meal meal) {

    }


    // when delete the the meal  ask in dialog Frist
    @Override
    public void onFavoriteDeleted(Meal meal) {
        // Create an AlertDialog to confirm deletion
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Meal")
                .setMessage("Are you sure you want to delete this meal from your favorites?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Yes, proceed with deletion
                        favoritePresenter.deleteMeal(meal);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked No, do nothing
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .show();
    }


    @Override
    public void onImageClicked(String imageId) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action =
                FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(imageId);
        Navigation.findNavController(navView).navigate(action);
    }
}