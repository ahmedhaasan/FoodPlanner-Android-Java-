package com.example.foodplanneritiandroidjava.view.details;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.presenter.mealDetails.DetailsPresenter;
import com.example.foodplanneritiandroidjava.presenter.plans.PlannedPresenter;
import com.example.foodplanneritiandroidjava.view.Ingrediants.IngrediantsAdapter;
import com.example.foodplanneritiandroidjava.view.details.MealDetailsFragmentArgs;
import com.example.foodplanneritiandroidjava.view.login_signUp.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealDetailsFragment extends Fragment implements DetailsContract {

    private RecyclerView mealIngrediantRecycler;
    private ImageView detailedImage;
    private ImageView detailedPlanIcon;
    FloatingActionButton detailedFavIcon;
    private TextView detailMealName, detailMealCategory, detailMealCountry, instructionSteps;
    private WebView mealVideo;

    private LinearLayoutManager ingrediantManager;
    private IngrediantsAdapter ingrediantsAdapter;
    private DetailsPresenter detailsPresenter;
    private PlannedPresenter plannedPresenter;
    private String mealId;
    private List<Meal> meal;

    boolean isGuest ;

    //
 /*   List<Ingredient> mealIngrediants;
    FavoritePresenter favoritePresenter;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
            mealId = args.getMealID();
        }

        // get user state from shared
        SharedPreferences prefs = requireActivity().getSharedPreferences(SomeContstants.USERSTATE, getContext().MODE_PRIVATE);
        // Retrieve the guest status and assign it to the instance variable
        isGuest = prefs.getBoolean(SomeContstants.ISGUEST, false);
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
        detailedFavIcon = view.findViewById(R.id.add_to_favorite_button);
        detailedPlanIcon = view.findViewById(R.id.add_to_plan_button);
        detailMealName = view.findViewById(R.id.meal_name);
        detailMealCategory = view.findViewById(R.id.meal_category);
        detailMealCountry = view.findViewById(R.id.meal_country);
        mealVideo = view.findViewById(R.id.meal_videoView);
        instructionSteps = view.findViewById(R.id.mealsStepsField);

        // Initialize layout manager and set the adapter
        ingrediantManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ingrediantsAdapter = new IngrediantsAdapter(getContext(), new ArrayList<>());
        mealIngrediantRecycler.setLayoutManager(ingrediantManager);
        mealIngrediantRecycler.setHasFixedSize(true);
        mealIngrediantRecycler.setAdapter(ingrediantsAdapter);

        // Initialize presenter and fetch meal details
        detailsPresenter = new DetailsPresenter(new MealParentReposiatory(
                new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())), this);
        detailsPresenter.getMealDetail(mealId);

     /*   // favPresenter
        favoritePresenter = new FavoritePresenter(new MealParentReposiatory(
                new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())));
        plannedPresenter = new PlannedPresenter(new MealParentReposiatory(
                new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())));
        detailsPresenter.getMealDetail(mealId);
        // detailsPresenter.getMealDetail(mealId);*/

        // Initialize favorite button click listener
        handleFavoritePress();

        // intialize planned button clicked
        detailedPlanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGuest) {
                    showLoginDialog();
                } else{
                    showDaySelectionDialog();
                    //Toast.makeText(getContext(), "added sucssesfully", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onShowDetails(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            this.meal = meals;

            // Update UI with the meal details
            if (getView() != null) {
                Glide.with(getContext())
                        .load(meal.get(0).getThumb())
                        .apply(new RequestOptions().override(200, 200))
                        .into(detailedImage);

                detailMealName.setText(meal.get(0).getName());
                detailMealCategory.setText(meal.get(0).getCategory());
                detailMealCountry.setText(meal.get(0).getCountry());
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

            // Handle video
            String videoUrl = meal.get(0).getVideoUrl();
            if (videoUrl != null && !videoUrl.isEmpty()) {
                String videoId = extractVideoId(videoUrl);
                if (videoId != null) {
                    String videoHtml = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"
                            + videoId + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay;" +
                            " clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
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

    @Override
    public void onMealDetailsFails(String error) {
        Toast.makeText(getContext(), "error fetching mealDetails"+error, Toast.LENGTH_SHORT).show();
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


    // /on favorite clicked

    private void handleFavoritePress() {

        detailedFavIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use the guest status as needed
                if (isGuest) {
                    showLoginDialog();
                } else {
                    detailsPresenter.insertMealIntoFavorite(meal.get(0));
                    Toast.makeText(getContext(), "Added To Favorite Successfully ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    // dialog to tell user you are not register
    // dialog to show guest user to go and signIn
    private void showLoginDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Login Required")
                .setMessage("You need to log in to access this feature.")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle login action
                        // navigate to login to as guest
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("Navigation", "login");
                        startActivity(intent);

                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /// method to handel plan icon

    public void showDaySelectionDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_spinner, null);

        // Find the Spinner and OK button
        Spinner spinnerDays = dialogView.findViewById(R.id.spinner_days);
        Button btnOk = dialogView.findViewById(R.id.ok_button);

        // Setup Spinner with days of the week
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.days_of_week, android.R.layout.simple_spinner_item);  // here is the array of days
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDays.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Set the OK button click listener
        btnOk.setOnClickListener(v -> {
            // Get the selected day from Spinner
            String selectedDay = spinnerDays.getSelectedItem().toString();
            if (meal != null && !meal.isEmpty()) { // Check if meal is initialized
                handleSelectedDay(selectedDay,getDateOfDayInCurrentWeek(selectedDay));
                Toast.makeText(getContext(), "Added to planned day successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Meal is not available", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss(); // Close the dialog
        });

        dialog.show(); // Show the dialog
    }


    private void handleSelectedDay(String selectedDay,String date ) {
        // Logic to handle the selected day
        // For example, adding the selected day to the plan
        PlannedMeal plannedMeal = new PlannedMeal(meal.get(0).getId(), meal.get(0).getName(),
                meal.get(0).getCategory(), meal.get(0).getCountry(), meal.get(0).getThumb(), selectedDay,date);
        detailsPresenter.insertMealIntoPlanned(plannedMeal);
    }



    // function to handel date
    public String getDateOfDayInCurrentWeek(String dayName) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        // Find the start of the current week (e.g., Sunday or Monday)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        for (int i = 0; i < 7; i++) {
            String currentDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
            if (currentDay.equalsIgnoreCase(dayName)) {
                return sdf.format(calendar.getTime());
            }
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }

        return null;  // If the dayName doesn't match any day in the current week
    }

}
