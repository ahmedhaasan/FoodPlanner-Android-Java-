package com.example.foodplanneritiandroidjava.view.home.homeActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.foodplanneritiandroidjava.AnetworkStatues.NetworkChangeListener;
import com.example.foodplanneritiandroidjava.AnetworkStatues.NetworkChangeReceiver;
import com.example.foodplanneritiandroidjava.AnetworkStatues.NetworkUtils;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Country;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.presenter.Country.CountriesPresenter;
import com.example.foodplanneritiandroidjava.presenter.Ingrediants.IngrediantsPresenter;
import com.example.foodplanneritiandroidjava.presenter.category.CategoryPresenter;
import com.example.foodplanneritiandroidjava.presenter.dailyMeal.DailyMealPresenter;
import com.example.foodplanneritiandroidjava.view.home.Ingrediants.IngrediantsAdapter;
import com.example.foodplanneritiandroidjava.view.home.Ingrediants.IngrediantsContract;
import com.example.foodplanneritiandroidjava.view.home.category.CategoryAdapter;
import com.example.foodplanneritiandroidjava.view.home.category.CategoryContract;
import com.example.foodplanneritiandroidjava.view.home.countries.CountriesAdapter;
import com.example.foodplanneritiandroidjava.view.home.countries.CountriesContract;
import com.example.foodplanneritiandroidjava.view.home.dailyMeals.OnDailyMealContract;
import com.example.foodplanneritiandroidjava.view.login_signUp.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements OnDailyMealContract, CategoryContract , IngrediantsContract
, CountriesContract , NetworkChangeListener {


    CardView dailyMealCardView ;
    ImageView dailyMealImage;
    TextView dailyMealName ;
    FloatingActionButton addToFavorite ;
    RecyclerView categoryRecycler , ingrediantsRecycler,countriesRecycler;
    LinearLayoutManager categoryLayoutManager;

    GridLayoutManager ingrediantLayoutManager ;  // this manager for ingrediants

    /// lists
    List<Meal> randomMeal ;
    List<Category> categories;
    List<Ingredient> ingredients ;
    List<Country> countries ;
    // presenters
    DailyMealPresenter dailyPresenter;
    CategoryPresenter categoryPresenter ;
    IngrediantsPresenter ingrediantsPresenter;
    CountriesPresenter countriesPresenter ;
    // adapters
    CategoryAdapter categoryAdapter ;
    IngrediantsAdapter ingrediantsAdapter ;
    CountriesAdapter countriesAdapter ;
    boolean isGuest ;


    ScrollView home_scrollView ;
    /**************************************/
    private NetworkChangeReceiver networkChangeReceiver;
    LottieAnimationView noInternet_animation ;

    /**************************************/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        // Initialize views
        SharedPreferences prefs = requireActivity().getSharedPreferences(SomeContstants.USERSTATE, getContext().MODE_PRIVATE);
        // Retrieve the guest status
        boolean isGuest = prefs.getBoolean(SomeContstants.ISGUEST, false);
        dailyMealCardView = view.findViewById(R.id.dailyMealCardView);
        dailyMealImage = view.findViewById(R.id.dailyMealImage);
        dailyMealName = view.findViewById(R.id.categoryName);
        addToFavorite = view.findViewById(R.id.daily_add_to_fav);

        // Initialize RecyclerView and set layout manager
        categoryRecycler = view.findViewById(R.id.categoryRecycler1);
        categoryRecycler.setHasFixedSize(true);
        categoryLayoutManager = new LinearLayoutManager(getContext());
        categoryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecycler.setLayoutManager(categoryLayoutManager);

        // ingrediant recycler
        ingrediantsRecycler = view.findViewById(R.id.ingredientsRecycler);
        // below line for divide the recycleer view
        ingrediantLayoutManager=  new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        ingrediantsRecycler.setLayoutManager(ingrediantLayoutManager);
        ingrediantsRecycler.setHasFixedSize(true);
        ingrediantLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        // countries recycler
        countriesRecycler = view.findViewById(R.id.countriesRecycler);
        countriesRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        countriesRecycler.setLayoutManager(layoutManager);



        // Initialize data lists
        randomMeal = new ArrayList<>();
        categories = new ArrayList<>();
        ingredients = new ArrayList<>();
        countries = new ArrayList<>();

        // Initialize adapter and set it to RecyclerView
        categoryAdapter = new CategoryAdapter(getContext(),categories);
        categoryRecycler.setAdapter(categoryAdapter);
        ingrediantsAdapter = new IngrediantsAdapter(getContext(),ingredients,this);
        ingrediantsRecycler.setAdapter(ingrediantsAdapter);
        countriesAdapter = new CountriesAdapter(getContext(),countries);
        countriesRecycler.setAdapter(countriesAdapter);


        // Initialize presenters
        dailyPresenter = new DailyMealPresenter(new MealParentReposiatory(new MealsRemoteDataSource(),new  MealsLocalDataSource(getContext())), this);
        dailyPresenter.geDailyMeal();

        categoryPresenter = new CategoryPresenter(new MealParentReposiatory(new MealsRemoteDataSource(),new MealsLocalDataSource(getContext())), this);
        categoryPresenter.getCategories();

        ingrediantsPresenter = new IngrediantsPresenter(new MealParentReposiatory(new MealsRemoteDataSource(),new MealsLocalDataSource(getContext())),this);
        ingrediantsPresenter.getIngrediants();

        countriesPresenter = new CountriesPresenter(new MealParentReposiatory(new MealsRemoteDataSource(),new MealsLocalDataSource(getContext())),this);
        countriesPresenter.getAllCountries();

        /**************************/
        noInternet_animation = view.findViewById(R.id.no_internet_animation);
        home_scrollView = view.findViewById(R.id.home_scrollView);
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

        // action on add To Fav
        dailyMealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController = Navigation.findNavController(view);
                HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                        HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(randomMeal.get(0).getId());
                navController.navigate(action);
            }
        });

        // handel when the add To favorite is pressed on daily meal image
        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isGuest) {
                    showLoginDialog();

                }else {
                    if (!randomMeal.isEmpty()) {
                        Log.d("HomeFragment", "RandomMeal size: " + randomMeal.size());
                        dailyPresenter.onAddToFavoritePressed(randomMeal.get(0));
                        Toast.makeText(getContext(), "Added To Favorite Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "No meal available to add to favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    /// dialog for return user to login if guest
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


    ///

    private void updateMealCard(List<Meal> meal) {
        dailyMealName.setText(meal.get(0).getName());
        // Load image using Glide
        Glide.with(this)
                .load(meal.get(0).getThumb())
                .into(dailyMealImage);
    }

    // implemented from
    @Override
    public void showDailyMeals(List<Meal> meals) {
        randomMeal.addAll(meals);
        if (!meals.isEmpty()) {
            updateMealCard(meals);
        }
    }


    @Override
    public void showDailyError(String message) {

    }

    // those methods implementd from Category Contract
    @Override
    public void showsCategories(List<Category> categories) {

        categoryAdapter.setCategoryList(categories);
    }

    @Override
    public void showCategoriesError(String message) {

    }

    // implemented from Ingrediants Shows
    @Override
    public void showsIngrediants(List<Ingredient> ingredients) {
        ingrediantsAdapter.setIngredientsList(ingredients);
    }

    @Override
    public void showsIngrediantsError(String message) {

    }

    @Override

    public void onIngredianImagePressed(String ingrediantName, String SearchType) {
        // Ensure the view is not null
        View view = getView();
        if (view != null) {
            // Perform the navigation
            HomeFragmentDirections.ActionHomeFragmentToMealFragment action =
                    HomeFragmentDirections.actionHomeFragmentToMealFragment(ingrediantName, SearchType);
            Navigation.findNavController(view).navigate(action);
        } else {
            // Handle the case where the view is null, possibly log an error or show a message
            Log.e("HomeFragment", "View is null, cannot navigate.");
        }
    }


    @Override
    public void showsCountries(List<Country> countries) {

        countriesAdapter.setCountries(countries);

    }

    @Override
    public void showCountriesError(String message) {

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
        home_scrollView.setVisibility(View.GONE);
        if (noInternet_animation != null) {
            noInternet_animation.setVisibility(View.VISIBLE);
            noInternet_animation.playAnimation();  // Ensure the animation plays
        }

    }

    private void enableFragment() {
        // Logic to enable the fragment when the internet connection is restored
        refreshFragment();
        home_scrollView.setVisibility(View.VISIBLE);
        if (noInternet_animation != null) {
            noInternet_animation.setVisibility(View.GONE);
            noInternet_animation.cancelAnimation();  // Stop the animation
        }
        Toast.makeText(requireContext(), "Fragment Enabled", Toast.LENGTH_SHORT).show();
    }


    private void refreshFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.homeFragment);

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.detach(fragment);
            transaction.attach(fragment);
            transaction.commit();
        }
    }
    // to unregister the connection
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister the receiver when the fragment view is destroyed
        requireContext().unregisterReceiver(networkChangeReceiver);
    }
}



    // from Countries Contract

