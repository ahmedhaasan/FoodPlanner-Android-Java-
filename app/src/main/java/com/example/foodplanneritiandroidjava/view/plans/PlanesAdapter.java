package com.example.foodplanneritiandroidjava.view.plans;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PlanesAdapter extends  RecyclerView.Adapter<PlanesAdapter.ViewHolder> {

    List<PlannedMeal> plannedMeals ;
    PlansFragment plansFragment ;
    Context context ;

    public PlanesAdapter(Context context, List<PlannedMeal> plannedMeals, PlansFragment plansFragment) {
        this.plannedMeals = new ArrayList<>(plannedMeals); // Create a copy of the list
        this.plansFragment = plansFragment;
        this.context = context;
    }
    //
    public void setPlannedMeals(List<PlannedMeal> plannedMeals) {
        this.plannedMeals.clear();
        this.plannedMeals.addAll(plannedMeals);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.plan_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PlannedMeal  plannedMeal = plannedMeals.get(position);
        holder.plannedMealName.setText(plannedMeal.getName());
        holder.plannedCategoryName.setText(plannedMeal.getCategory());
        holder.plannedCountryName.setText(plannedMeal.getCountry());
        holder.plannedMealDay.setText(plannedMeal.getDay());
        Glide.with(context)
                .load(plannedMeal.getThumb())
                .apply(new RequestOptions().override(200, 200))
                .into(holder.plannedMealImage);
        // work on action here
        // action on image pressed
        holder.plannedMealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plansFragment.onPlannedImageAdded(plannedMeal.getId());
                Log.i("tryID",plannedMeal.getId());
            }
        });

        // action when press delete

        holder.deletePlanned_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plansFragment.onPlannedDeleted(plannedMeal);
            }
        });

    }

    @Override
    public int getItemCount() {
        return plannedMeals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView plannedMealImage;
        //ImageView plannedAddToFav_icon,
        FloatingActionButton deletePlanned_icon;
        TextView plannedMealDay, plannedCountryName, plannedCategoryName;
        TextView plannedMealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.plannedMealImage = itemView.findViewById(R.id.favorite_meal_image);
            //this.plannedAddToFav_icon = itemView.findViewById(R.id.plan_favorite_icon);
            this.deletePlanned_icon = itemView.findViewById(R.id.delete_plan_icon);
            this.plannedMealDay = itemView.findViewById(R.id.plan_day_Name);
            this.plannedCountryName = itemView.findViewById(R.id.plan_country_name);
            this.plannedCategoryName = itemView.findViewById(R.id.favorite_meal_category);
            this.plannedMealName = itemView.findViewById(R.id.plan_meal_name);
        }
    }
}
