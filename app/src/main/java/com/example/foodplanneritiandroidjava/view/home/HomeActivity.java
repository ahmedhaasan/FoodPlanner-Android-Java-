package com.example.foodplanneritiandroidjava.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foodplanneritiandroidjava.R;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.view.login_signUp.login.view.login_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Fragment homeFragment;
    FragmentManager manager;
    BottomNavigationView navigationView,menuView;  // this is for navigating between items
    DrawerLayout drawerLayout;
    NavController navController ;

    // instance form fire base to logOut :
    private FirebaseAuth firebaseAuth;


    List<Category> mycategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        firebaseAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth

        setContentView(R.layout.home_activity);

        navigationView = findViewById(R.id.nav_button);
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        // drawer
        drawerLayout = findViewById(R.id.main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.home_itm);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

/*        manager = getSupportFragmentManager(); // get instanse of manager
        homeFragment = new HomeFragment();
        FragmentTransaction trans = manager.beginTransaction();
        trans.add(R.id.homeFragmentContainer, homeFragment, "Dynamic Fragment");
        trans.commit();*/

        /******************************************************/


    }

    // this for open and close the drawer

    ///


    ///


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); // Replace 'your_menu_file' with your actual menu file name
        return true;
    }

    // to handel menu items

        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
            }
            return super.onOptionsItemSelected(item);

        }
    //NavigationUI.setupWithNavController(navigationView, navController);
// Set up navigation item selected listener




}
