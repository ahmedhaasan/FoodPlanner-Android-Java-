package com.example.foodplanneritiandroidjava.view.Ingrediants;

import android.content.Context;
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
import com.example.foodplanneritiandroidjava.model.PojoClasses.Ingredient;

import java.util.List;


public class IngrediantsAdapter extends RecyclerView.Adapter<IngrediantsAdapter.ViewHolder> {

    List<Ingredient> ingredients;
    Context context;

    public IngrediantsAdapter(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    public void setIngredientsList(List<Ingredient> ingredients) {
        this.ingredients.clear();
        this.ingredients.addAll(ingredients);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.ingediant_row,parent,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Ingredient ingredient = ingredients.get(position);
        holder.IngrediantName.setText(ingredient.getName());
        Glide.with(context)
                .clear(holder.IngrediantImage); // Clears any previous image
        Glide.with(context)
                .load(ingredient.getThumb())
                .apply(new RequestOptions().override(200, 200))
                .into(holder.IngrediantImage);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.with(context).clear(holder.IngrediantImage);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView IngrediantName;
        ImageView IngrediantImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            IngrediantImage = itemView.findViewById(R.id.ingredient_image);
            IngrediantName = itemView.findViewById(R.id.ingredient_field);
        }
    }
}
