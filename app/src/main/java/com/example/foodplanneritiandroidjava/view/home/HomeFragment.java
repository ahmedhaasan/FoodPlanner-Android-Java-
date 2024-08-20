package com.example.foodplanneritiandroidjava.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.example.foodplanneritiandroidjava.view.favorite.FavoriteFragmentDirections;
import com.example.foodplanneritiandroidjava.view.home.Ingrediants.IngrediantsAdapter;
import com.example.foodplanneritiandroidjava.view.home.Ingrediants.IngrediantsContract;
import com.example.foodplanneritiandroidjava.view.home.category.CategoryAdapter;
import com.example.foodplanneritiandroidjava.view.home.category.CategoryContract;
import com.example.foodplanneritiandroidjava.view.home.countries.CountriesAdapter;
import com.example.foodplanneritiandroidjava.view.home.countries.CountriesContract;
import com.example.foodplanneritiandroidjava.view.home.dailyMeals.OnDailyMealContract;
import com.example.foodplanneritiandroidjava.view.home.details.MealDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnDailyMealContract, CategoryContract , IngrediantsContract
, CountriesContract {


    CardView dailyMealCardView ;
    ImageView dailyMealImage;
    TextView dailyMealName ;
    RecyclerView categoryRecycler , ingrediantsRecycler,countriesRecycler;
    LinearLayoutManager categoryLayoutManager,ingrediantLayoutManager;

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
        dailyMealCardView = view.findViewById(R.id.dailyMealCardView);
        dailyMealImage = view.findViewById(R.id.dailyMealImage);
        dailyMealName = view.findViewById(R.id.dailyMealName);

        // Initialize RecyclerView and set layout manager
        categoryRecycler = view.findViewById(R.id.categoryRecycler1);
        categoryRecycler.setHasFixedSize(true);
        categoryLayoutManager = new LinearLayoutManager(getContext());
        categoryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecycler.setLayoutManager(categoryLayoutManager);

        // ingrediant recycler
        ingrediantsRecycler = view.findViewById(R.id.ingredientsRecycler);
        ingrediantsRecycler.setHasFixedSize(true);
        ingrediantLayoutManager = new LinearLayoutManager(getContext());
        ingrediantLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ingrediantsRecycler.setLayoutManager(ingrediantLayoutManager);

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

    }


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
     updateMealCard(meals);
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
}


    // from Countries Contract

