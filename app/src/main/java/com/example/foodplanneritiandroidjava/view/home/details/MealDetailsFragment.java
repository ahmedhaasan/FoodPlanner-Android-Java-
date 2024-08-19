package com.example.foodplanneritiandroidjava.view.home.details;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.presenter.mealDetails.DetailsPresenter;
import com.example.foodplanneritiandroidjava.view.home.Ingrediants.IngrediantsAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealDetailsFragment extends Fragment implements DetailsContract {

    private RecyclerView mealIngrediantRecycler;
    private ImageView detailedImage;
    private ImageView detailedFavIcon, detailedPlanIcon;
    private TextView detailMealName, detailMealCategory, detailMealCountry ,instructionSteps;
    private VideoView mealVideo;

    private LinearLayoutManager ingrediantManager;
    private IngrediantsAdapter ingrediantsAdapter;
    private DetailsPresenter detailsPresenter;
    private String mealId;
    private List<Meal> meal;
    List<Ingredient> mealIngrediants ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
            mealId = args.getMealID();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        mealIngrediantRecycler = view.findViewById(R.id.ingrediant_detail_recycler);
        detailedImage = view.findViewById(R.id.meal_image);
        detailedFavIcon = view.findViewById(R.id.add_favorite_image_icon);
        detailedPlanIcon = view.findViewById(R.id.addTo_plan_image_icon);
        detailMealName = view.findViewById(R.id.meal_name);
        detailMealCategory = view.findViewById(R.id.meal_category_name);
        detailMealCountry = view.findViewById(R.id.meal_country_name);
        mealVideo = view.findViewById(R.id.meal_videoView);
        instructionSteps = view.findViewById(R.id.mealsStepsField);


        // Initialize layout manager and set the adapter
        ingrediantManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ingrediantsAdapter = new IngrediantsAdapter(getContext(), new ArrayList<>());
        mealIngrediantRecycler.setLayoutManager(ingrediantManager);
        mealIngrediantRecycler.setHasFixedSize(true);
        mealIngrediantRecycler.setAdapter(ingrediantsAdapter);


        // Initialize presenter and fetch meal details
        detailsPresenter = new DetailsPresenter(new MealParentReposiatory
                (new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())), this);
        detailsPresenter.getMealDetail(mealId);
    }

    @Override
    public void onShowDetails(List<Meal> meals) {
        // Ensure meal is not null and has at least one item
        if (meals != null && !meals.isEmpty()) {
            this.meal = meals;

            // Update UI with the meal details
            if (getView() != null) {
                // Safely access the views and update their contents
                Glide.with(getContext())
                        .load(meal.get(0).getThumb())
                        .apply(new RequestOptions().override(200, 200))
                        .into(detailedImage);

                if (detailMealName != null) {
                    detailMealName.setText(meal.get(0).getName());
                }
                if (detailMealCategory != null) {
                    detailMealCategory.setText(meal.get(0).getCategory());
                }
                if (detailMealCountry != null) {
                    detailMealCountry.setText(meal.get(0).getCountry());
                }
            }

            // handel ingrediants
            List<Ingredient> mealIngrediants = Arrays.asList(
                    new Ingredient(meal.get(0).getStrIngredient1()),
                    new Ingredient(meal.get(0).getStrIngredient2()),
                    new Ingredient(meal.get(0).getStrIngredient3()),
                    new Ingredient(meal.get(0).getStrIngredient4()),
                    new Ingredient(meal.get(0).getStrIngredient5()),
                    new Ingredient(meal.get(0).getStrIngredient6()),
                    new Ingredient(meal.get(0).getStrIngredient7()),
                    new Ingredient(meal.get(0).getStrIngredient8()),
                    new Ingredient(meal.get(0).getStrIngredient9()),
                    new Ingredient(meal.get(0).getStrIngredient10()),
                    new Ingredient(meal.get(0).getStrIngredient11()),
                    new Ingredient(meal.get(0).getStrIngredient12()),
                    new Ingredient(meal.get(0).getStrIngredient13()),
                    new Ingredient(meal.get(0).getStrIngredient14()),
                    new Ingredient(meal.get(0).getStrIngredient15()),
                    new Ingredient(meal.get(0).getStrIngredient16()),
                    new Ingredient(meal.get(0).getStrIngredient17()),
                    new Ingredient(meal.get(0).getStrIngredient18()),
                    new Ingredient(meal.get(0).getStrIngredient19()),
                    new Ingredient(meal.get(0).getStrIngredient20())
            );
            ingrediantsAdapter.setIngredientsList(mealIngrediants);
        }
    }
}
