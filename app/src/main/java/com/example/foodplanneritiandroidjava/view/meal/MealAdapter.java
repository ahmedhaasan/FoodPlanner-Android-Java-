package com.example.foodplanneritiandroidjava.view.meal;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.view.home.Ingrediants.IngrediantsAdapter;

import java.util.List;

public class MealAdapter extends  RecyclerView.Adapter<MealAdapter.ViewHolder> {

    List<Meal> mealList ;
    Context context ;

    public MealAdapter(Context context ,List<Meal> mealList) {
        this.mealList = mealList;
        this.context = context ;
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
        Meal meal = mealList.get(position);
        holder.mealName.setText(meal.getName());
        holder.countryName.setText(meal.getCountry());
        holder.categoryName.setText(meal.getCategory());

        Glide.with(context)
                .load(meal.getThumb())
                .apply(new RequestOptions().override(200, 200))
                .into(holder.mealImage);

        // on image press\
        holder.mealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MealFragmentDirections.ActionMealFragmentToMealDetailsFragment action =
                        MealFragmentDirections.actionMealFragmentToMealDetailsFragment(meal.getId());
                Navigation.findNavController(view).navigate(action);
            }
        });
        // Handle favorite and calendar icons here
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
