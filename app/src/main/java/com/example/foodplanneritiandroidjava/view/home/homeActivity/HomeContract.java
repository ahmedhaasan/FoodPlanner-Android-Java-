package com.example.foodplanneritiandroidjava.view.home.homeActivity;

public interface HomeContract {

    void showLoading();
    void hideLoading();
    void showUploadSuccessMessage();
    void showDownloadSuccessMessage();
    void showErrorMessage(String message);
}
