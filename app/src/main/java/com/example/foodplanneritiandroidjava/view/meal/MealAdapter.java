package com.example.foodplanneritiandroidjava.view.meal;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.presenter.favorite.FavoritePresenter;
import com.example.foodplanneritiandroidjava.view.favorite.FavoriteFragment;
import com.example.foodplanneritiandroidjava.view.favorite.FavoriteFragmentDirections;
import com.example.foodplanneritiandroidjava.view.home.Ingrediants.IngrediantsAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MealAdapter extends  RecyclerView.Adapter<MealAdapter.ViewHolder> {

    List<Meal> mealList ;
    Context context ;
    Fragment fragment ;

    public MealAdapter(Context context , List<Meal> mealList, Fragment fragment) {
        this.mealList = mealList;
        this.context = context ;
        this.fragment = fragment ;
    }

    public void setMealList(List<Meal> mealList){
        this.mealList.clear();
        this.mealList.addAll(mealList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.meal_row,parent,false);

        return  new MealAdapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal MealT = mealList.get(position);
        holder.mealName.setText(MealT.getName());
        holder.countryName.setText(MealT.getCountry());
        holder.categoryName.setText(MealT.getCategory());

        Glide.with(context)
                .load(MealT.getThumb())
                .apply(new RequestOptions().override(200, 200))
                .into(holder.mealImage);

        // on image press\
        holder.mealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);

                if (fragment instanceof MealFragment) {
                    MealFragmentDirections.ActionMealFragmentToMealDetailsFragment action =
                            MealFragmentDirections.actionMealFragmentToMealDetailsFragment(MealT.getId());
                    navController.navigate(action);
                } else if (fragment instanceof FavoriteFragment) {
                    FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action =
                            FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(MealT.getId());
                    navController.navigate(action);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      ImageView mealImage ,favoriteIcon,planIcon;
      TextView categoryName , countryName,mealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.meal_image);
            favoriteIcon = itemView.findViewById(R.id.add_favorite_image_icon);
            planIcon = itemView.findViewById(R.id.addTo_plan_image_icon);
            categoryName = itemView.findViewById(R.id.meal_category_name);
            countryName = itemView.findViewById(R.id.meal_country_name);
            mealName = itemView.findViewById(R.id.meal_name);

        }
    }
}
