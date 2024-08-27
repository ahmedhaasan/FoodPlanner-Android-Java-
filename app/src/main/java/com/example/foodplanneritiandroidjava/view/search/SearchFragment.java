package com.example.foodplanneritiandroidjava.view.search;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanneritiandroidjava.networkStatus.NetworkChangeListener;
import com.example.foodplanneritiandroidjava.networkStatus.NetworkChangeReceiver;
import com.example.foodplanneritiandroidjava.networkStatus.NetworkUtils;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Country;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.view.meal.MealAdapter;
import com.example.foodplanneritiandroidjava.presenter.search.SearchPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchContract, NetworkChangeListener{

    private EditText searchMealField;
    // private Button searchMealButton;
    private ChipGroup chipMealGroup;
    private Chip categoriesChip, countriesChip, ingrediantsChip, mealNameChip, mealIdChip;
    private RecyclerView searchRecycler;
    private GridLayoutManager searchMealManager;

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
    Chip TempChip = null;

    /**************************************/
    private NetworkChangeReceiver networkChangeReceiver;
    LottieAnimationView noInternet_animation;
    ConstraintLayout mainContent_layout;
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
        mainContent_layout = view.findViewById(R.id.main_search_content_layout);
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
        searchPresenter = new SearchPresenter(reposiatory, this);
        for (char letter = 'a'; letter <= 'z'; letter++) {
            searchPresenter.getMealsByFristLetter(String.valueOf(letter));

        }
        // fetching all countries and all ingrediants and all categories
        //
        categories = new ArrayList<>();
        countries = new ArrayList<>();
        ingredients = new ArrayList<>();
        searchPresenter = new SearchPresenter(new MealParentReposiatory(
                new MealsRemoteDataSource(), new MealsLocalDataSource(requireContext())),this);
        searchPresenter.getAllCountries();
        searchPresenter.getIngridiants();
        searchPresenter.makeCategoryCallBack();
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
                    mealAdapter.setMealList(filteredMeals);  // selecting from all meals and add to adapter
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
                TempChip = checkedChip ;
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
        String query = searchMealField.getText().toString().toLowerCase();
        if (chipId == R.id.category_chip) {
            searchMealsByCategory(query);
        } else if (chipId == R.id.countries_chip) {
            searchMealsByCountry(query);
        } else if (chipId == R.id.ingrediants_chip) {
            searchMealsByIngredient(query);
        }
    }

    private void searchMealsByCategory(String query) {
        for (Category category : categories) {
            if (category.getName().toLowerCase().contains(query)) {
                searchPresenter.getMealsByCategoryName(category.getName());
                mealAdapter.setMealList(mealList);
            }
        }
    }

    private void searchMealsByCountry(String query) {
        for (Country country : countries) {
            if (country.getName().toLowerCase().contains(query)) {
                searchPresenter.getMealsByCountry(country.getName());
                mealAdapter.setMealList(mealList);
            }
        }
    }

    private void searchMealsByIngredient(String query) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().toLowerCase().contains(query)) {
                searchPresenter.getMealsByIngrediant(ingredient.getName());
                mealAdapter.setMealList(mealList);
            }
        }
    }



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


        // from search  Contract

    @Override
    public void onSearchSuccess(List<Meal> searchedMeals) {
        if (searchedMeals != null && !searchedMeals.isEmpty()) {
            // Add the fetched meals to the full list

            if(TempChip != null) {

                    mealList.addAll(searchedMeals);
            }
            allMeals.addAll(searchedMeals);

        }
    }

    @Override
    public void onSearchFail(String message) {
        Toast.makeText(getContext(), "failed To search "+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessCategories(List<Category> categories) {
        if (categories != null) {
            this.categories.addAll(categories);
            for (Category category : categories) {
                Log.i("kola", category.getName());
            }
        }
    }

    @Override
    public void showFailCatigories(String message) {
        Toast.makeText(getContext(), "fail To get Categories" + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showSuccessCountries(List<Country> countries) {
        if (countries != null) {
            this.countries.addAll(countries);
            for (Country country : countries) {
                Log.i("kola", country.getName());
            }
        }
    }

    @Override
    public void showFailCountries(String message) {
        Toast.makeText(getContext(), "Fail to Get Countries" + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showSuccessIngrediants(List<Ingredient> ingredients) {
        if (ingredients != null) {
            this.ingredients.addAll(ingredients);
            for (Ingredient ingredient : ingredients) {
                Log.i("kola", ingredient.getName());
            }
        }
    }

    @Override
    public void showFailIngrediants(String message) {
        Toast.makeText(getContext(), "Error to get Ingrediants" + message, Toast.LENGTH_SHORT).show();

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
            mainContent_layout.setVisibility(View.GONE);
            noInternet_animation.playAnimation();  // Ensure the animation plays
        }
    }

    private void enableFragment() {
        // Logic to enable the fragment when the internet connection is restored
        refreshFragment();
        mainContent_layout.setVisibility(View.VISIBLE);
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
