package com.example.foodplanneritiandroidjava.view.login_signUp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanneritiandroidjava.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.splash_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.splash_fragment, true) // Pop the splash fragment from the back stack
                    .build();

            if (currentUser != null) {
                // User is signed in, navigate to the home activity
                Navigation.findNavController(view).navigate(R.id.action_splash_fragment_to_homeActivity, null, navOptions);
            } else {
                // No user is signed in, navigate to the login fragment
                Navigation.findNavController(view).navigate(R.id.action_splash_fragment_to_login_fragment, null, navOptions);
            }
        }, 3000); // Delay of 3 seconds
    }
}