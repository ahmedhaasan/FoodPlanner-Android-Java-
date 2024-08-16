package com.example.foodplanneritiandroidjava.view.login_signUp.login.presenter;

import com.example.foodplanneritiandroidjava.view.login_signUp.login.LoginCallback;
import com.example.foodplanneritiandroidjava.view.login_signUp.login.view.LoginView;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter {
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModel();
    }

    public void handleEmailPasswordLogin(String email, String password) {
        loginModel.loginWithEmailAndPassword(email, password, new LoginCallback() {
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
        loginModel.loginWithGoogle(idToken, new LoginCallback() {
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
}

