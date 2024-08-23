package com.example.foodplanneritiandroidjava.view.search;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanneritiandroidjava.AnetworkStatues.NetworkChangeListener;
import com.example.foodplanneritiandroidjava.AnetworkStatues.NetworkChangeReceiver;
import com.example.foodplanneritiandroidjava.AnetworkStatues.NetworkUtils;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Country;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.CategoriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.CountriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.IngrediantsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.presenter.meal.MealPresenter;
import com.example.foodplanneritiandroidjava.view.meal.MealAdapter;
import com.example.foodplanneritiandroidjava.view.search.presenter.SearchPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchContract, NetworkChangeListener,
        MealsCallBack, CategoriesCallBack, CountriesCallBack, IngrediantsCallBack {

    private EditText searchMealField;
    // private Button searchMealButton;
    private ChipGroup chipMealGroup;
    private Chip categoriesChip, countriesChip, ingrediantsChip, mealNameChip, mealIdChip;
    private RecyclerView searchRecycler;
    private GridLayoutManager searchMealManager;

    // Meal presenter instance
    private MealPresenter mealPresenter;
    // Meal adapter
    private MealAdapter mealAdapter;
    // Meal list
    private List<Meal> mealList;
    private List<Meal> allMeals;

    // 
    private List<Category> categories;
    private List<Ingredient> ingredients;
    private List<Country> countries;

    SearchPresenter searchPresenter;
    ///
    // Remote source
    private MealParentReposiatory reposiatory;
    private String mealSourceType;

    /**************************************/
    private NetworkChangeReceiver networkChangeReceiver;
    LottieAnimationView noInternet_animation;
    ScrollView searc_scrollView;
    View v2;

    /**************************************/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialization
        mealList = new ArrayList<>();
        allMeals = new ArrayList<>();
        mealAdapter = new MealAdapter(getContext(), mealList, this);
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
        v2 = view;
        // Initialize UI components
        //searchMealButton = view.findViewById(R.id.searchForMeal_button);
        searchMealField = view.findViewById(R.id.searchForMeal_field);
        chipMealGroup = view.findViewById(R.id.chip_group);
        categoriesChip = view.findViewById(R.id.category_chip);
        countriesChip = view.findViewById(R.id.countries_chip);
        ingrediantsChip = view.findViewById(R.id.ingrediants_chip);

        // RecyclerView setup
        searchRecycler = view.findViewById(R.id.mealSearchRecycler);
        searchRecycler.setHasFixedSize(true);
        searchMealManager = new GridLayoutManager(getContext(),2);
        searchMealManager.setOrientation(RecyclerView.VERTICAL);
        searchRecycler.setLayoutManager(searchMealManager);
        searchRecycler.setAdapter(mealAdapter);

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
        mealPresenter = new MealPresenter(reposiatory, this, mealSourceType);
        for (char letter = 'a'; letter <= 'z'; letter++) {
            mealPresenter.getMealsByFristLetter(String.valueOf(letter));

        }
        // fetching all countries and all ingrediants and all categories
        //
        categories = new ArrayList<>();
        countries = new ArrayList<>();
        ingredients = new ArrayList<>();
        searchPresenter = new SearchPresenter(new MealParentReposiatory(new MealsRemoteDataSource(), new MealsLocalDataSource(requireContext())));
        searchPresenter.getAllCountries(this);
        searchPresenter.getIngridiants(this);
        searchPresenter.makeCategoryCallBack(this);
        // here i got all the cat and countries and ingre

        //

        // when searc
        searchMealField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                int chickedChipIp = chipMealGroup.getCheckedChipId();
                // Check if the key event is a key down event
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && chickedChipIp == View.NO_ID) {
                    Log.i("hola", getChipName().toLowerCase());
                    String query = searchMealField.getText().toString().toLowerCase();
                    List<Meal> filteredMeals = new ArrayList<>();
                    for (Meal meal : allMeals) {
                        if (meal.getName().toLowerCase().contains(query)) {
                            filteredMeals.add(meal);
                        }
                    }
                    mealAdapter.setMealList(filteredMeals);
                }
                return false; // Added to ensure a boolean is returned in all cases
            }
        });

        ////

        chipMealGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Log.d("SearchFragment", "Chip Checked ID: " + checkedId);

                if (checkedId == View.NO_ID) {
                    Log.d("SearchFragment", "No chip selected");
                    return;
                }
                Chip checkedChip = group.findViewById(checkedId);

                if (checkedChip != null) {
                    Log.d("SearchFragment", "Chip Checked Name: " + checkedChip.getText().toString());
                    handleChipSelection(checkedChip.getId()); // Call method to handle the selection
                } else {
                    Log.d("SearchFragment", "No chip found with ID: " + checkedId);
                }
            }
        });
    }


    private void handleChipSelection(int chipId) {
        if (chipId == R.id.category_chip) {
            Log.d("SearchFragment", "Category chip selected");
            String query = searchMealField.getText().toString().toLowerCase();
            for (Category category : categories) {
                if (category.getName().toLowerCase().contains(query)) {
                    searchPresenter.getMealsByCategoryName(SearchFragment.this, category.getName());

                    mealAdapter.setMealList(mealList);
                }
            }
            // Handle category chip selection
        } else if (chipId == R.id.countries_chip) {
            Log.d("SearchFragment", "Countries chip selected");
            String query = searchMealField.getText().toString().toLowerCase();
            for (Country country : countries) {
                if (country.getName().toLowerCase().contains(query)) {
                    searchPresenter.getMealsByCountry(SearchFragment.this, country.getName());
                    mealAdapter.setMealList(mealList);
                }

            }
            // Handle countries chip selection
        } else if (chipId == R.id.ingrediants_chip) {
            Log.d("SearchFragment", "Ingredients chip selected");
            String query = searchMealField.getText().toString().toLowerCase();
            for (Ingredient ingredient : ingredients) {
                if (ingredient.getName().toLowerCase().contains(query)) {
                    searchPresenter.getMealsByCountry(SearchFragment.this, ingredient.getName());
                    mealAdapter.setMealList(mealList);
                }

            }
            // Handle ingredients chip selection
        } else {
            Log.d("SearchFragment", "Other chip selected");
            // Handle other chips if necessary
        }
    }

    // Set up the search button action
    /*    searchMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the adapter with all the fetched meals
                if (allMeals.isEmpty()) {
                    Toast.makeText(getContext(), "No meals found.", Toast.LENGTH_SHORT).show();
                } else {
                    mealAdapter.setMealList(allMeals);
                }
            }
        });*/


    /*********************************/


    /*****************************/


    // check the chip id

    String getChipName() {
        String chipName = "";
        int checkedChipId = chipMealGroup.getCheckedChipId();

        Log.d("SearchFragment", "Checked Chip ID: " + checkedChipId);

        if (checkedChipId != View.NO_ID) {
            Chip checkedChip = chipMealGroup.findViewById(checkedChipId);

            if (checkedChip != null) {
                chipName = checkedChip.getText().toString();
                Log.d("SearchFragment", "Checked Chip Name: " + chipName);
            } else {
                Log.d("SearchFragment", "No chip found with ID: " + checkedChipId);
            }
        } else {
            Log.d("SearchFragment", "No chip is selected");
        }

        return chipName;
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


    // these methods implemented using meals Call back and category CallBAck
    @Override
    public void onMealsSuccess(List<Meal> meals) {
        if (meals != null) {
            mealList.addAll(meals);
        /*    for (Meal meal : meals) {
                Log.i("mealsDone", meal.getName());
            }*/
        }
    }

    @Override
    public void onMealsFailure(String message) {

        Toast.makeText(getContext(), "faliled To Get meals " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessCategory(List<Category> categories) {
        if (categories != null) {
            this.categories.addAll(categories);
            for (Category category : categories) {
                Log.i("kola", category.getName());
            }
        }
    }

    @Override
    public void onFailurResult(String message) {

        Toast.makeText(getContext(), "fail To get Categories" + message, Toast.LENGTH_SHORT).show();
    }


    // implemented from countries and ingrediants callback
    @Override
    public void onCountriesSuccess(List<Country> countries) {
        if (countries != null) {
            this.countries.addAll(countries);
            for (Country country : countries) {
                Log.i("kola", country.getName());
            }
        }
    }

    @Override
    public void onCountriesFails(String message) {
        Toast.makeText(getContext(), "Fail to Get Countries" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccesIngrediants(List<Ingredient> ingredients) {

        if (ingredients != null) {
            this.ingredients.addAll(ingredients);
            for (Ingredient ingredient : ingredients) {
                Log.i("kola", ingredient.getName());
            }
        }
    }

    @Override
    public void onFailIngridiants(String message) {

        Toast.makeText(getContext(), "Error to get Ingrediants" + message, Toast.LENGTH_SHORT).show();
    }


}
