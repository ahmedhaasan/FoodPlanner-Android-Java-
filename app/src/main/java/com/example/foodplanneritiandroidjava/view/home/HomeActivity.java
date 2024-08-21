package com.example.foodplanneritiandroidjava.view.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.view.login_signUp.MainActivity;
import com.example.foodplanneritiandroidjava.view.login_signUp.login.view.login_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Fragment homeFragment;
    FragmentManager manager;
    BottomNavigationView navigationView;
    // this is for navigating between items
    NavigationView drawerNav;
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
        // drawer
        drawerLayout = findViewById(R.id.mainDrawer);
        drawerNav=findViewById(R.id.navigation);
        navigationView = findViewById(R.id.nav_button);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(drawerNav,navController);
        NavigationUI.setupWithNavController(navigationView, navController);

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

        drawerNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (item.getItemId() == R.id.upload_data) {
                    // Handle upload data action
                    // Toast.makeText(HomeActivity.this, "Upload Data clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.download_data) {
                    // Handle download data action
                    // Toast.makeText(HomeActivity.this, "Download Data clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.log_out) {
                    firebaseAuth.signOut();

                    // Use HomeActivity.this to refer to the Activity context
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    intent.putExtra("Navigation", "login");
                    startActivity(intent);
                    finish();

                /*    NavOptions navOptions = new NavOptions.Builder()
                            .setPopUpTo(R.id.login_fragment, true) // Clear the back stack up to splash fragment
                            .build();
                    navController.navigate(R.id.login_fragment, null, navOptions);
                    finish();*/

                    Toast.makeText(HomeActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                }


                if (id == R.id.favoriteFragment) {
                    if (isUserGeust()) {
                        showLoginDialog();
                        return true; // Return true to indicate that the click was handled
                    }
                } else if (id == R.id.plansFragment) {
                    if (isUserGeust()) {
                        showLoginDialog();
                        return true; // Return true to indicate that the click was handled
                    }
                }


                return false;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.button_bar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle drawer opening/closing
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return super.onOptionsItemSelected(item);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }


    // check if the user is guest

    boolean isUserGeust(){
                  SharedPreferences prefs;
                prefs = getSharedPreferences(SomeContstants.GUESTUSER, Context.MODE_PRIVATE);
                boolean isGuest = prefs.getBoolean(SomeContstants.ISGUEST, false);
        return isGuest;
    }

    //

    // dialog to show guest user to go and signIn
    private void showLoginDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Required")
                .setMessage("You need to log in to access this feature.")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle login action
                        // You might want to navigate to the login fragment or activity
                        NavController navController = Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment);
                        navController.navigate(R.id.login_fragment);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }



}
