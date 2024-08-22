package com.example.foodplanneritiandroidjava.view.search;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanneritiandroidjava.AnetworkStatues.NetworkChangeListener;
import com.example.foodplanneritiandroidjava.AnetworkStatues.NetworkChangeReceiver;
import com.example.foodplanneritiandroidjava.AnetworkStatues.NetworkUtils;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.presenter.meal.MealPresenter;
import com.example.foodplanneritiandroidjava.view.meal.MealAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchContract, NetworkChangeListener {

    private EditText searchMealField;
    private Button searchMealButton;
    private ChipGroup chipMealGroup;
    private Chip categoriesChip, countriesChip, ingrediantsChip, mealNameChip, mealIdChip;
    private RecyclerView searchRecycler;
    private LinearLayoutManager searchMealManager;

    // Meal presenter instance
    private MealPresenter mealPresenter;
    // Meal adapter
    private MealAdapter mealAdapter;
    // Meal list
    private List<Meal> mealList;
    private List<Meal> allMeals;
    // Remote source
    private MealParentReposiatory reposiatory;
    private String mealSourceType;

    /**************************************/
    private NetworkChangeReceiver networkChangeReceiver;
    LottieAnimationView noInternet_animation ;
    ScrollView searc_scrollView ;
    /**************************************/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialization
        mealList = new ArrayList<>();
        allMeals = new ArrayList<>();
        mealAdapter = new MealAdapter(getContext(), mealList,this);
        reposiatory = MealParentReposiatory.getInstance(new MealsRemoteDataSource(), new MealsLocalDataSource(getContext()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI components
        searchMealButton = view.findViewById(R.id.searchForMeal_button);
        searchMealField = view.findViewById(R.id.searchForMeal_field);
        chipMealGroup = view.findViewById(R.id.chip_group);
        categoriesChip = view.findViewById(R.id.category_chip);
        countriesChip = view.findViewById(R.id.countries_chip);
        ingrediantsChip = view.findViewById(R.id.ingrediants_chip);
        mealNameChip = view.findViewById(R.id.meal_name_chip);
        mealIdChip = view.findViewById(R.id.meal_id_chip);

        // RecyclerView setup
        searchRecycler = view.findViewById(R.id.mealSearchRecycler);
        searchRecycler.setHasFixedSize(true);
        searchMealManager = new LinearLayoutManager(getContext());
        searchMealManager.setOrientation(RecyclerView.VERTICAL);
        searchRecycler.setLayoutManager(searchMealManager);
        searchRecycler.setAdapter(mealAdapter);
        mealPresenter = new MealPresenter(reposiatory, this, mealSourceType);

        /**************************/
        noInternet_animation = view.findViewById(R.id.no_internet);
        searc_scrollView = view.findViewById(R.id.searc_scrollView);
        // check for network
        // Check initial connectivity status
        if (!NetworkUtils.isConnectedToInternet(requireContext())) {
            disableFragment();
            //showNoInternetMessage();
        }

        // Register the receiver to listen for connectivity changes
        networkChangeReceiver = new NetworkChangeReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        requireContext().registerReceiver(networkChangeReceiver, filter);


        /**************************/


        // Fetch meals for each letter from a to z
        for (char letter = 'a'; letter <= 'z'; letter++) {
            mealPresenter.getMealsByFristLetter(String.valueOf(letter));
        }

        // Set up the search button action
        searchMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the adapter with all the fetched meals
                if (allMeals.isEmpty()) {
                    Toast.makeText(getContext(), "No meals found.", Toast.LENGTH_SHORT).show();
                } else {
                    mealAdapter.setMealList(allMeals);
                }
            }
        });

        searchMealField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                // Check if the key event is a key down event
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    String query = searchMealField.getText().toString().toLowerCase();
                    List<Meal> filteredMeals = new ArrayList<>();
                    for (Meal meal : allMeals) {
                        if (meal.getName().toLowerCase().contains(query)) {
                            filteredMeals.add(meal);
                        }
                    }
                    mealAdapter.setMealList(filteredMeals);
                }
                return false;
            }
        });
    }

    @Override
    public void showSearchList(List<Meal> searchedMeals) {
        if (searchedMeals != null && !searchedMeals.isEmpty()) {
            // Add the fetched meals to the full list
            allMeals.addAll(searchedMeals);
        }
    }

    // this method from Network Change Listener to check the Connectivity of the network

    @Override
    public void onNetworkChanged(boolean isConnected) {
        if (isConnected) {
            // Internet connection restored
            Toast.makeText(requireContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
            enableFragment();
        } else {
            // Internet connection lost
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            disableFragment();
        }

    }

    // needed for network connection

    private void disableFragment() {
        // Logic to disable specific functionality within the fragment
        Toast.makeText(requireContext(), "Fragment Disabled Due to No Internet", Toast.LENGTH_SHORT).show();
        if (noInternet_animation != null) {
            noInternet_animation.setVisibility(View.VISIBLE);
            searc_scrollView.setVisibility(View.GONE);
            noInternet_animation.playAnimation();  // Ensure the animation plays
        }

    }

    private void enableFragment() {
        // Logic to enable the fragment when the internet connection is restored
        refreshFragment();
        searc_scrollView.setVisibility(View.VISIBLE);
        if (noInternet_animation != null) {
            noInternet_animation.setVisibility(View.GONE);
            noInternet_animation.cancelAnimation();  // Stop the animation
        }
        Toast.makeText(requireContext(), "Fragment Enabled", Toast.LENGTH_SHORT).show();
    }


    private void refreshFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.searchFragment);

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.detach(fragment);
            transaction.attach(fragment);
            transaction.commit();
        }
    }
}
