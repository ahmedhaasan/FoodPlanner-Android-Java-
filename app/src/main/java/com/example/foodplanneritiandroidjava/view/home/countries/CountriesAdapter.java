package com.example.foodplanneritiandroidjava.view.home.countries;

import android.content.Context;
import android.util.Log;
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
import com.example.foodplanneritiandroidjava.model.PojoClasses.Country;
import com.example.foodplanneritiandroidjava.view.home.HomeFragmentDirections;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
    List<Country> countries;
    Context context;

    public CountriesAdapter(Context context, List<Country> countries) {
        this.context = context;
        this.countries = countries;
    }


    public void setCountries(List<Country> countries){
        if (countries != null && !countries.isEmpty()) {
            this.countries.clear();
            this.countries.addAll(countries);
        }
        Log.d("CountriesAdapter", "Countries list size: " + this.countries.size());
        notifyDataSetChanged();  if (countries != null) {
            this.countries.clear();
            this.countries.addAll(countries);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.country_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Country country = countries.get(position);
        holder.countryName.setText(country.getName());
        // this for image
        Glide.with(context)
                .clear(holder.countryImage); // Clears any previous image
        Glide.with(context)
                .load(country.getThumb())
                .apply(new RequestOptions().override(200, 200))
                .into(holder.countryImage);

        // make an action when press on category to went to meal Fragment and shows the meals of this category

        holder.countryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToMealFragment action =
                        HomeFragmentDirections.actionHomeFragmentToMealFragment(country.getName(), SomeContstants.COUNTRY);
                Navigation.findNavController(view).navigate(action);
            }
        });

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView countryName;
        ImageView countryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryImage = itemView.findViewById(R.id.countryImage); // Use the correct ID
            countryName = itemView.findViewById(R.id.countryName);   // Use the correct ID
        }
    }
}

