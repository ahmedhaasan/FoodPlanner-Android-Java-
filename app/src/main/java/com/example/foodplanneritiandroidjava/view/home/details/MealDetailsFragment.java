package com.example.foodplanneritiandroidjava.view.home.details;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealDetailsFragment extends Fragment implements DetailsContract {

    private RecyclerView mealIngrediantRecycler;
    private ImageView detailedImage;
    private ImageView detailedFavIcon, detailedPlanIcon;
    private TextView detailMealName, detailMealCategory, detailMealCountry ,instructionSteps;
    private WebView mealVideo;

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

            // Handle ingredients
            List<Ingredient> mealIngredients = Arrays.asList(
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
            ingrediantsAdapter.setIngredientsList(mealIngredients);

            // Handle steps
            String instructions = meal.get(0).getInstructions();
            if (instructions != null && !instructions.isEmpty()) {
                String[] steps = instructions.split("\r\n");
                StringBuilder formattedInstructions = new StringBuilder();
                for (int i = 0; i < steps.length; i++) {
                    formattedInstructions.append(i + 1).append(". ").append(steps[i].trim()).append("\n\n");
                }
                instructionSteps.setText(formattedInstructions.toString().trim());
            } else {
                instructionSteps.setText(R.string.no_instructions_available);
            }

            //


            // Handle video
            String videoUrl = meal.get(0).getVideoUrl();
            if (videoUrl != null && !videoUrl.isEmpty()) {
                String videoId = extractVideoId(videoUrl);
                if (videoId != null) {
                    String videoHtml = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
                    mealVideo.getSettings().setJavaScriptEnabled(true);
                    mealVideo.setWebChromeClient(new WebChromeClient());
                    mealVideo.loadData(videoHtml, "text/html", "utf-8");
                } else {
                    Toast.makeText(requireContext(), "Invalid video URL", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "No video available for this meal", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String extractVideoId(String youtubeUrl) {
        String videoId = null;
        if (youtubeUrl != null) {
            Pattern pattern = Pattern.compile("v=([\\w-]+)");
            Matcher matcher = pattern.matcher(youtubeUrl);
            if (matcher.find()) {
                videoId = matcher.group(1);
            }
        }
        return videoId;
    }

}
