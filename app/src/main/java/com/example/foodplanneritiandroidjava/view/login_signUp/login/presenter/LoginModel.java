package com.example.foodplanneritiandroidjava.view.login_signUp.login.presenter;

import androidx.annotation.NonNull;

import com.example.foodplanneritiandroidjava.view.login_signUp.login.LoginCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginModel {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase ;

    public LoginModel(){
        // create instances
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void loginWithEmailAndPassword(String email,String password,final LoginCallback callback){
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            callback.onSuccess(firebaseAuth.getCurrentUser());

                        }else{
                            callback.onFailure(task.getException().getLocalizedMessage());

                        }
                    }
                });
    }

    // login with google

    public void loginWithGoogle(String idToken, final LoginCallback callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name", user.getDisplayName());
                        map.put("email", user.getEmail());
                        map.put("profile", user.getPhotoUrl().toString());
                        firebaseDatabase.getReference().child("googleAuth").child(user.getUid())
                                .setValue(map)
                                .addOnSuccessListener(aVoid -> callback.onSuccess(user))
                                .addOnFailureListener(e -> callback.onFailure(e.getLocalizedMessage()));
                    } else {
                        callback.onFailure(task.getException().getLocalizedMessage());
                    }
                });
    }

    public void createUserWithEmailAndPassword(String email, String password, final LoginCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("email", user.getEmail());
                            firebaseDatabase.getReference().child("users").child(user.getUid())
                                    .setValue(map)
                                    .addOnSuccessListener(aVoid -> callback.onSuccess(user))
                                    .addOnFailureListener(e -> callback.onFailure(e.getLocalizedMessage()));
                        } else {
                            callback.onFailure(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }
}
