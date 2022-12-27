package com.example.fitnessadvisor;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fitnessadvisor.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(this);
        navView.setSelectedItemId(R.id.navigation_account);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_workout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, WorkoutListFragment.class, null).commit();
                return true;

            case R.id.navigation_nutrition:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, NutritionFragment.class, null).commit();
                return true;

            case R.id.navigation_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, AccountFragment.class, null).commit();
                return true;
        }
        return false;
    }

}