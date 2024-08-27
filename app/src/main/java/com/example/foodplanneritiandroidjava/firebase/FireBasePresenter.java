package com.example.foodplanneritiandroidjava.firebase;

import android.content.Context;

import com.example.foodplanneritiandroidjava.view.homeActivity.HomeActivity;
import com.example.foodplanneritiandroidjava.view.login_signUp.login.LoginCallback;
import com.example.foodplanneritiandroidjava.view.login_signUp.login.view.LoginView;
import com.google.firebase.auth.FirebaseUser;

public class FireBasePresenter implements  FireBasePresenterService {
    private LoginView loginView;
    private FireBaseReposiatory fireBaseReposiatory;
    HomeActivity homeActivity ;
    Context context ;

    public FireBasePresenter(LoginView loginView) {
        this.loginView = loginView;
        this.fireBaseReposiatory = new FireBaseReposiatory();
    }

    public FireBasePresenter (HomeActivity homeActivity, FireBaseReposiatory fireBaseReposiatory,Context context) {
        this.homeActivity = homeActivity;
        this.fireBaseReposiatory = fireBaseReposiatory;
        this.context = context ;
    }

    /********************************************/
    /********************************************/
    /********************************************/


    public void handleEmailPasswordLogin(String email, String password) {

        fireBaseReposiatory.loginWithEmailAndPassword(email, password, new LoginCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                loginView.onLoginSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                loginView.onLoginFailure(errorMessage);
            }
        });
    }

    public void handleGoogleLogin(String idToken) {
        fireBaseReposiatory.loginWithGoogle(idToken, new LoginCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                loginView.onLoginSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                loginView.onLoginFailure(errorMessage);
            }
        });
    }


    // for upload data and dawnload data

    // and firebase reposiatory but it defined above

    @Override
    public void uploadData(String userId) {

        homeActivity.showLoading();
        fireBaseReposiatory.uploadDataToFirebase(userId, new DataUploadCallback() {
            @Override
            public void onUploadSuccess() {
                homeActivity.hideLoading();
            }

            @Override
            public void onUploadFailure(String errorMessage) {
                homeActivity.hideLoading();
                homeActivity.showErrorMessage(errorMessage);
            }
        },context);
        homeActivity.showUploadSuccessMessage();

    }

    @Override
    public void downloadData(String userId) {

        homeActivity.showLoading();
        fireBaseReposiatory.downloadDataFromFirebase(userId, new DataDownloadCallback() {
            @Override
            public void onDawnloadSuccess() {
                homeActivity.hideLoading();
            }

            @Override
            public void onDawnloadFails(String errorMessage) {

                homeActivity.hideLoading();
                homeActivity.showErrorMessage(errorMessage);
            }
        },context);
        homeActivity.showDownloadSuccessMessage();

    }
}

