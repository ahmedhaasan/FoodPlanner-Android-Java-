    package com.example.foodplanneritiandroidjava.view.plans;

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
    import android.widget.Toast;

    import com.example.foodplanneritiandroidjava.R;
    import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
    import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
    import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
    import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
    import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
    import com.example.foodplanneritiandroidjava.presenter.plans.PlannedPresenter;
    import com.example.foodplanneritiandroidjava.view.favorite.FavoriteFragmentDirections;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;

    public class PlansFragment extends Fragment implements PlannedContract {


        RecyclerView plannedRecyclerView;
        LinearLayoutManager plansManager;
        PlanesAdapter planesAdapter ;
        PlannedPresenter plannedPresenter ;
        // list of Live planned list

        // view to use when navigate
        View navView ;

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

            navView = view ;
            ////////////////
            plannedRecyclerView = view.findViewById(R.id.planned_recycler);
            plansManager = new LinearLayoutManager(getContext());
            plannedRecyclerView.setHasFixedSize(true);
            plansManager.setOrientation(RecyclerView.VERTICAL);
            plannedRecyclerView.setLayoutManager(plansManager);

            planesAdapter = new PlanesAdapter(getContext(), new ArrayList<>(),this);
            plannedRecyclerView.setAdapter(planesAdapter);

            // Initialize planned presenter
            plannedPresenter = new PlannedPresenter(new MealParentReposiatory(new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())), this);
            LiveData<List<PlannedMeal>> livePlannedMeals = plannedPresenter.getAllPlanned();
            livePlannedMeals.observe(getViewLifecycleOwner(), new Observer<List<PlannedMeal>>() {
                @Override
                public void onChanged(List<PlannedMeal> meals) {
                    Log.d("PlansFragment", "Data received: " + (meals != null ? meals.size() : 0) + " items");
                    planesAdapter.setPlannedMeals(meals); // Update adapter with fetched data
                }
            });

        }


        // implemented from plannedContract

        @Override
        public LiveData<List<Meal>> showPlannedMeals() {

            return null;
        }

        @Override
        public void showPlannedMealsWithData(LiveData<List<PlannedMeal>> plannedMeals) {

        }

        @Override
        public void showPlannedError(String error) {

        }

        @Override
        public void onPlannedCliced(PlannedMeal plannedMeal) {

        }

        // manage when delete
        @Override
        public void onPlannedDeleted(PlannedMeal plannedMeal) {

            plannedPresenter.deletePlannedMealById(plannedMeal.getId());
            Toast.makeText(getContext(), "Deleted SuccessFully", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPlannedAddedToFavorite(PlannedMeal plannedMeal) {

        }

        @Override
        public void onPlannedImageAdded(String mealId) {
//            FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action =
//                    FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(imageId);
//            Navigation.findNavController(getActivity().getCurrentFocus()).navigate(action);
            // to navigate to meal details when a press
            PlansFragmentDirections.ActionPlansFragmentToMealDetailsFragment action =
                    PlansFragmentDirections.actionPlansFragmentToMealDetailsFragment(mealId);
            Navigation.findNavController(navView).navigate(action);


        }
    }