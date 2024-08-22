package com.example.foodplanneritiandroidjava.view.home.homeActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodplanneritiandroidjava.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.firebase.FireBasePresenter;
import com.example.foodplanneritiandroidjava.firebase.FireBaseReposiatory;
import com.example.foodplanneritiandroidjava.model.PojoClasses.Category;
import com.example.foodplanneritiandroidjava.view.login_signUp.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeContract {

    Dialog loadingDialog;
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
        firebaseAuth = FirebaseAuth.getInstance();

        //EdgeToEdge.enable(this);
        setContentView(R.layout.home_activity);
        // drawer this for drawer
        drawerLayout = findViewById(R.id.mainDrawer);
        drawerNav=findViewById(R.id.navigation);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(drawerNav,navController);

        // this for // button navigation
        navigationView = findViewById(R.id.nav_button);
        NavigationUI.setupWithNavController(navigationView, navController);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.home_itm);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        /******************************************************/
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        /************************************************/

        FireBasePresenter presenter = new FireBasePresenter(this,new FireBaseReposiatory(),this);
        drawerNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (item.getItemId() == R.id.upload_data) {
                    // Handle upload data action
                    // Toast.makeText(HomeActivity.this, "Upload Data clicked", Toast.LENGTH_SHORT).show();

                    presenter.uploadData(firebaseAuth.getUid());
                } else if (item.getItemId() == R.id.download_data) {

                   presenter.downloadData(firebaseAuth.getUid());

                    // Handle download data action
                    // Toast.makeText(HomeActivity.this, "Download Data clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.log_out) {
                    signOut();

                    Toast.makeText(HomeActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        // this for  button navigation items work on it

        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                NavController navController = Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment);

                // Check if the user is a guest and handle navigation accordingly
                if (isUserGeust()) {
                    if (id == R.id.favoriteFragment || id == R.id.plansFragment) {
                        showLoginDialog();
                        return true; // Prevent further processing
                    }
                }

                // Handle navigation based on item ID
                if (id == R.id.favoriteFragment) {
                    navController.navigate(R.id.favoriteFragment);
                } else if (id == R.id.homeFragment) {
                    navController.navigate(R.id.homeFragment);
                } else if (id == R.id.searchFragment) {
                    navController.navigate(R.id.searchFragment);
                } else if (id == R.id.plansFragment) {
                    navController.navigate(R.id.plansFragment);
                }

                return true;
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
                        // navigate to login to as guest
                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        intent.putExtra("Navigation", "login");
                        startActivity(intent);
                        finish();

                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    // signOut from FireBase and Google authentication
    public void signOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Sign out from Firebase
        auth.signOut();

        // Clear any additional session-related data (e.g., SharedPreferences)
        SharedPreferences prefs = getSharedPreferences(SomeContstants.GUESTUSER, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SomeContstants.ISGUEST, true); // Reset to guest status or any other value
        editor.apply();

        // If using Google Sign-In, also sign out from Google
        GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();

        // Redirect to login screen
        // Use HomeActivity.this to refer to the Activity context
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Navigation", "login");
        startActivity(intent);
        finish();
    }



    // methods from  Home contract ;
    @Override
    public void showLoading() {

        // Show a loading spinner or progress bar
        loadingDialog.show(); // Show loading dialog when login starts
     /*   // Disable user interaction if necessary
        setUp(false);*/
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss(); // Show loading dialog when login starts

    }

    @Override
    public void showUploadSuccessMessage() {
        Toast.makeText(this, "Data uploaded successfully!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showDownloadSuccessMessage() {
        Toast.makeText(this, "Data downloaded successfully!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_LONG).show();

    }
}
