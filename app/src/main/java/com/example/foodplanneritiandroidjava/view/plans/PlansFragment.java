package com.example.foodplanneritiandroidjava.view.plans;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foodplanneritiandroidjava.networkStatus.NetworkUtils;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.presenter.plans.PlannedPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PlansFragment extends Fragment implements PlannedContract {


    RecyclerView plannedRecyclerView;
    LinearLayoutManager plansManager;
    PlanesAdapter planesAdapter;
    PlannedPresenter plannedPresenter;
    // list of Live planned list

  /*  // view to use when navigate
    View navView;*/
    Spinner filterSpenner; // Add this line
    ArrayList <PlannedMeal> FilteredList=new ArrayList<>();


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

       /* navView = view;*/
        ////////////////
        plannedRecyclerView = view.findViewById(R.id.planned_recycler);
        plansManager = new LinearLayoutManager(getContext());
        plannedRecyclerView.setHasFixedSize(true);
        plansManager.setOrientation(RecyclerView.VERTICAL);
        plannedRecyclerView.setLayoutManager(plansManager);
        filterSpenner = view.findViewById(R.id.days_filter_spinner); // Initialize Spinner


        planesAdapter = new PlanesAdapter(getContext(), new ArrayList<>(), this);
        plannedRecyclerView.setAdapter(planesAdapter);

        // Initialize planned presenter
        plannedPresenter = new PlannedPresenter(new MealParentReposiatory(
                new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())), this);
        LiveData<List<PlannedMeal>> livePlannedMeals = plannedPresenter.getAllPlanned();
        livePlannedMeals.observe(getViewLifecycleOwner(), new Observer<List<PlannedMeal>>() {
                @Override
                public void onChanged(List<PlannedMeal> meals) {
                    FilteredList.addAll(meals);
                    Log.d("PlansFragment", "Data received: " + (meals != null ? meals.size() : 0) + " items");
                    planesAdapter.setPlannedMeals(meals); // Update adapter with fetched data
                }
        });


                // Set up Spinner for select the day and filter using it
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        getContext(), R.array.days_of_week, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                filterSpenner.setAdapter(adapter);

                filterSpenner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedDay = (String) parent.getItemAtPosition(position);

                       // filterMealsByDay(selectedDay); // Call method to filter meals
                        planesAdapter.setPlannedMeals(mealsOfWeek(FilteredList,selectedDay));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

    }

    ArrayList<PlannedMeal> mealsOfWeek(List<PlannedMeal> meals,String Day) {
        ArrayList<String> startAndEnd = getStartAndEndDate();
        ArrayList<PlannedMeal>Result=new ArrayList<>();
        for (int i = 0; i < meals.size(); i++) {
            PlannedMeal m=meals.get(i);
            int a=compareDates(startAndEnd.get(0),meals.get(i).getDate())
                    ;
            int b=compareDates(startAndEnd.get(1),meals.get(i).getDate());
            if (compareDates(startAndEnd.get(0),meals.get(i).getDate())
                    <=0&&compareDates(startAndEnd.get(1),meals.get(i).getDate())>=0&&(Day.equals(getString(R.string.NONE))
                    ||Day.equals(meals.get(i).getDay()))){
                Result.add(meals.get(i));
            }
        }
        return Result;
    }


    ////////// get meals by day
    // Method to filter meals by the selected day
    private void filterMealsByDay(String day) {
        plannedPresenter.getAllPlannedMealsWithDate(day); // Call presenter method to get filtered meals
    }

    // implemented from plannedContract

    @Override
    public LiveData<List<Meal>> showPlannedMeals() {

        return null;
    }

    // manage when delete
    @Override
    public void onPlannedDeleted(PlannedMeal plannedMeal) {

        plannedPresenter.deletePlannedMealById(plannedMeal.getId());
        Toast.makeText(getContext(), "Deleted SuccessFully", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onPlannedImageAdded(String mealId) {
//            FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action =
//                    FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(imageId);
//            Navigation.findNavController(getActivity().getCurrentFocus()).navigate(action);
        // to navigate to meal details when a press
        if (NetworkUtils.isConnectedToInternet(getContext())) {
            PlansFragmentDirections.ActionPlansFragmentToMealDetailsFragment action =
                    PlansFragmentDirections.actionPlansFragmentToMealDetailsFragment(mealId);
            Navigation.findNavController(getView()).navigate(action);
        } else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Remember No you are Offline ")
                    .setMessage("Check Network and come again ")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }

    }


    ///////// this method return the  meals withen the date specified
    public ArrayList<String> getStartAndEndDate() {
        Calendar calendar = Calendar.getInstance();
        Date date= new Date();
        calendar.setTime(date);
        calendar.setTime(date);
        ArrayList<String> strings =new ArrayList<>();
        String o= calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        while (!Objects.requireNonNull(o).equalsIgnoreCase("Saturday")){
            calendar.add(Calendar.DAY_OF_YEAR,-1);
            o= calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.ENGLISH);
        }
        strings.add(sdf.format(calendar.getTime()));
        while (!Objects.requireNonNull(o).equalsIgnoreCase("Friday")){
            calendar.add(Calendar.DAY_OF_YEAR,1);
            o= calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.ENGLISH);
        }
        strings.add(sdf.format(calendar.getTime()));
        return strings;
    }

    //// this method compare the data

    public static int compareDates(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date parsedDate1 = sdf.parse(date1);
            Date parsedDate2 = sdf.parse(date2);
            return parsedDate1.compareTo(parsedDate2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Return 0 in case of parsing error } }
        }
    }
}
