package com.example.foodplanneritiandroidjava.presenter.Country;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Country;
import com.example.foodplanneritiandroidjava.model.network.CountriesCallBack;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.view.home.HomeFragment;

import java.util.List;

public class CountriesPresenter implements CountryPresenterService, CountriesCallBack {

    MealsRemoteDataSource remoteDataSource ;
    HomeFragment homeFragment ;

    public CountriesPresenter(MealsRemoteDataSource remoteDataSource, HomeFragment homeFragment) {
        this.remoteDataSource = remoteDataSource;
        this.homeFragment = homeFragment;
    }

    @Override
    public void getAllCountries() {
        remoteDataSource.getAllCountries(this);
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
