package com.example.foodplanneritiandroidjava.view.login_signUp.signUp.presenter;


import com.example.foodplanneritiandroidjava.view.login_signUp.login.LoginCallback;
import com.example.foodplanneritiandroidjava.firebase.FireBaseReposiatory;
import com.example.foodplanneritiandroidjava.view.login_signUp.signUp.view.RegisterView;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPresenter {
    private RegisterView registerView;
    private FireBaseReposiatory fireBaseReposiatory;

    public RegisterPresenter(RegisterView registerView) {
        this.registerView = registerView;
        this.fireBaseReposiatory = new FireBaseReposiatory();
    }

    public void handleUserRegistration(String email, String password) {
        fireBaseReposiatory.createUserWithEmailAndPassword(email, password, new LoginCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                registerView.onRegisterSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                registerView.onRegisterFailure(errorMessage);
            }
        });
    }
}
