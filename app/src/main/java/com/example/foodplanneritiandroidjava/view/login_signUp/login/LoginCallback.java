package com.example.foodplanneritiandroidjava.view.login_signUp.login;

import com.google.firebase.auth.FirebaseUser;

public interface LoginCallback {
    void onSuccess(FirebaseUser user);
    void onFailure(String errorMessage);

}
