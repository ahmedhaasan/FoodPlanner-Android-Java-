package com.example.foodplanneritiandroidjava.view.splashScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.view.homeActivity.HomeActivity;
import com.example.foodplanneritiandroidjava.view.login_signUp.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);



            // Hide the status bar and navigation bar

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_splash_screen);
        String userString = this.getSharedPreferences("user", Context.MODE_PRIVATE).getString("newUser", null);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d("SplashFragment", "Current user: " + (currentUser != null ? currentUser.getUid() : "No user"));

        new Handler().postDelayed(() -> {
            if (currentUser != null && userString != null) {
                // User is signed in, navigate to the home activity
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();

            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                finish();
            }
         }, 5000); // Delay of 1 second

}
}