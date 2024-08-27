package com.example.foodplanneritiandroidjava.presenter.Country;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Country;
import com.example.foodplanneritiandroidjava.model.network.CountriesCallBack;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.view.countries.CountriesContract;

import java.util.List;

public class CountriesPresenter implements CountryPresenterService, CountriesCallBack {

    MealParentReposiatory reposiatory;
    CountriesContract countriesContract;

    public CountriesPresenter(MealParentReposiatory reposiatory, CountriesContract countriesContract) {
        this.reposiatory = reposiatory;
        this.countriesContract = countriesContract;
    }

    @Override
    public void getAllCountries() {
        reposiatory.getAllCountries(this);
    }

    @Override
    public void onCountriesSuccess(List<Country> countries) {
        countriesContract.showsCountries(countries);
    }

    @Override
    public void onCountriesFails(String message) {

        countriesContract.showCountriesError(message);
    }
}
