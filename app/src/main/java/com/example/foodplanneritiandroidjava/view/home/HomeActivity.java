package com.example.foodplanneritiandroidjava.view.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Fragment homeFragment;
    FragmentManager manager;
    BottomNavigationView navigationView;  // this is for navigating between items

    List<Category> mycategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.home_activity);

        navigationView = findViewById(R.id.nav_button);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

/*        manager = getSupportFragmentManager(); // get instanse of manager
        homeFragment = new HomeFragment();
        FragmentTransaction trans = manager.beginTransaction();
        trans.add(R.id.homeFragmentContainer, homeFragment, "Dynamic Fragment");
        trans.commit();*/

    }


}