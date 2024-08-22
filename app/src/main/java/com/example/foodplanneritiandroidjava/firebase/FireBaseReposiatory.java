package com.example.foodplanneritiandroidjava.firebase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.foodplanneritiandroidjava.model.PojoClasses.Meal;
import com.example.foodplanneritiandroidjava.model.PojoClasses.PlannedMeal;
import com.example.foodplanneritiandroidjava.model.network.MealsRemoteDataSource;
import com.example.foodplanneritiandroidjava.model.reposatory.MealParentReposiatory;
import com.example.foodplanneritiandroidjava.model.reposatory.local.MealsLocalDataSource;
import com.example.foodplanneritiandroidjava.view.login_signUp.login.LoginCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FireBaseReposiatory implements  FireBaseModel {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firestore;

    public FireBaseReposiatory() {
        // create instances
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void loginWithEmailAndPassword(String email, String password, final LoginCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            callback.onSuccess(firebaseAuth.getCurrentUser());

                        } else {
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


        ///  these two functions form the FireBase Model to implement 7


        // here download the data






    }

    @Override
    public void uploadDataToFirebase(String userId, DataUploadCallback callback, Context context) {
        MealParentReposiatory reposiatory = MealParentReposiatory.getInstance(new MealsRemoteDataSource(), new MealsLocalDataSource(context));

        // Observe LiveData for favorite meals
        reposiatory.getAllLocalMealsMeals().observeForever(new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> favoriteMeals) {
                if (favoriteMeals != null && !favoriteMeals.isEmpty()) {
                    for (Meal meal : favoriteMeals) {
                        firestore.collection(userId)
                                .document("favoriteMeals")
                                .collection("meals")
                                .document(meal.getId()) // Use unique ID for each meal
                                .set(meal)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        callback.onUploadSuccess();
                                    } else {
                                        callback.onUploadFailure(String.valueOf(task.getException()));
                                    }
                                });
                    }
                }
            }
        });

        // Observe LiveData for planned meals
        reposiatory.getAllPlannedMeals().observeForever(new Observer<List<PlannedMeal>>() {
            @Override
            public void onChanged(List<PlannedMeal> plannedMeals) {
                if (plannedMeals != null && !plannedMeals.isEmpty()) {
                    for (PlannedMeal plannedMeal : plannedMeals) {
                        firestore.collection(userId)
                                .document("plannedMeals")
                                .collection("plannedMeals")
                                .document(plannedMeal.getId()) // Use unique ID for each planned meal
                                .set(plannedMeal)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        callback.onUploadSuccess();
                                    } else {
                                        callback.onUploadFailure(String.valueOf(task.getException()));
                                    }
                                });
                    }
                }
            }
        });
    }


    @Override
    public void downloadDataFromFirebase(String userId, DataDownloadCallback callback, Context context) {
        MealParentReposiatory reposiatory = MealParentReposiatory.getInstance(new MealsRemoteDataSource(), new MealsLocalDataSource(context));

        // Download favorite meals from Firestore
        firestore.collection(userId)
                .document("favoriteMeals")
                .collection("meals")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Meal> favoriteMeals = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            Meal meal = document.toObject(Meal.class);
                            if (meal != null) {
                                favoriteMeals.add(meal);
                            }
                        }
                        reposiatory.addAllLocalMeals(favoriteMeals);  // Add the meals to the local database
                        callback.onDawnloadSuccess();
                    } else {
                        callback.onDawnloadFails(String.valueOf(task.getException()));
                    }
                });

        // Download planned meals from Firestore
        firestore.collection(userId)
                .document("plannedMeals")
                .collection("plannedMeals")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<PlannedMeal> plannedMeals = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            PlannedMeal plannedMeal = document.toObject(PlannedMeal.class);
                            if (plannedMeal != null) {
                                plannedMeals.add(plannedMeal);
                            }
                        }
                        reposiatory.insertAllPlannedMeals(plannedMeals);  // Add the planned meals to the local database
                        callback.onDawnloadSuccess();
                    } else {
                        callback.onDawnloadFails(String.valueOf(task.getException()));
                    }
                });
    }

}
