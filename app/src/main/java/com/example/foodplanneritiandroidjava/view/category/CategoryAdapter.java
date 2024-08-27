package com.example.foodplanneritiandroidjava.view.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.view.homeActivity.HomeFragmentDirections;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<Category> categories;
    private Context context;
    CategoryContract categoryContract;


    public CategoryAdapter(Context context, List<Category> categories,CategoryContract categoryContract) {
        this.categories = categories;
        this.context = context;
        this.categoryContract = categoryContract ;
    }

    public void setCategoryList(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.categroy_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getName());
        // Load image using Glide
        Glide.with(context)
                .clear(holder.categoryImage); // Clears any previous image
        Glide.with(context)
                .load(category.getThumbnail())
                .placeholder(R.drawable.back_7)
                .apply(new RequestOptions().override(200, 200))
                .into(holder.categoryImage);

        // make an action when press on category to went to meal Fragment and shows the meals of this category

        holder.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            categoryContract.onCategoryImagePressed(category.getName(),SomeContstants.CATEGORY);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.with(context).clear(holder.categoryImage);
        holder.categoryImage.setImageDrawable(null); // Clear the image

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        ImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
        }
    }
}
