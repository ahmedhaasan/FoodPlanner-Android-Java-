package com.example.foodplanneritiandroidjava.view.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.view.meal.MealAdapter;
import com.example.foodplanneritiandroidjava.view.meal.MealFragment;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    List<Meal> mealList ;
  FavoriteFragment favoriteFragment ;
    Context context ;

    public FavoriteAdapter(FavoriteFragment favoriteFragment, List<Meal> mealList,Context context ) {
        this.mealList = mealList;
        this.favoriteFragment = favoriteFragment ;
        this.context = context ;
    }



    public void setMealList(List<Meal> mealList){
        this.mealList.clear();
        this.mealList.addAll(mealList);
        notifyDataSetChanged(); // Add this line
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.favorite_row,parent,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Meal MealT = mealList.get(position);
        holder.mealName.setText(MealT.getName());
        holder.countryName.setText(MealT.getCountry());
        holder.categoryName.setText(MealT.getCategory());

        Glide.with(context)
                .load(MealT.getThumb())
                .placeholder(R.drawable.back_7)
                .apply(new RequestOptions().override(200, 200))
                .into(holder.mealImage);

        // action when image clicked
        holder.mealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteFragment.onImageClicked(MealT.getId());
            }
        });

        // manage when deleteFavorite Cliced
        holder.deleteFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteFragment.onFavoriteDeleted(MealT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mealImage ,deleteFavoriteButton ;
        TextView categoryName , countryName,mealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.favorite_meal_image);
            deleteFavoriteButton = itemView.findViewById(R.id.delete_favoriteButton);
            categoryName = itemView.findViewById(R.id.favorite_meal_category);
            countryName = itemView.findViewById(R.id.favorite_meal_country);
            mealName = itemView.findViewById(R.id.favorite_meal_name);

        }
    }
}


