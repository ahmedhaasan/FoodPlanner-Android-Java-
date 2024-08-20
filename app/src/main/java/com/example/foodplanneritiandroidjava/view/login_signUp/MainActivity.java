package com.example.foodplanneritiandroidjava.view.login_signUp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanneritiandroidjava.R;

public class MainActivity extends AppCompatActivity {

    NavController navController ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
         navController = Navigation.findNavController(this,R.id.nav_host_fragment);
       // NavigationUI.setupActionBarWithNavController(this,navController);

        Intent intent = getIntent();
        String navTo = intent.getStringExtra("Navigation");
        if("login".equals(navTo)){
            navController.navigate(R.id.login_fragment);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp()|| super.onSupportNavigateUp();
    }
}