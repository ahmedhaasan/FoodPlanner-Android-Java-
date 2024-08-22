package com.example.foodplanneritiandroidjava.presenter.Country;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Country;
import com.example.foodplanneritiandroidjava.model.network.CountriesCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.home.homeActivity.HomeFragment;

import java.util.List;

public class CountriesPresenter implements CountryPresenterService, CountriesCallBack {

    MealParentReposiatory reposiatory;
    HomeFragment homeFragment ;

    public CountriesPresenter(MealParentReposiatory reposiatory, HomeFragment homeFragment) {
        this.reposiatory = reposiatory;
        this.homeFragment = homeFragment;
    }

    @Override
    public void getAllCountries() {
        reposiatory.getAllCountries(this);
    }

    @Override
    public void onCountriesSuccess(List<Country> countries) {
        homeFragment.showsCountries(countries);
    }

    @Override
    public void onCountriesFails(String message) {

        homeFragment.showCountriesError(message);
    }
}
