package com.shto.vehiclebulletin;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shto.vehiclebulletin.ui.login.LoginFragment;
import com.shto.vehiclebulletin.ui.vehicles.Vehicles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_vehicles, R.id.navigation_notifications)
                .build();

        //FragmentContainerView using findNavController
        //https://stackoverflow.com/questions/59275009/fragmentcontainerview-using-findnavcontroller
        //NavController navController = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment).find
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Check to see if the user is currently signed in
        FirebaseAuth.getInstance().signOut();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            navController.navigate(R.id.navigation_vehicles);
        } else {
            // No user is signed in
            Toast.makeText(this, "No user signed in", Toast.LENGTH_SHORT).show();

            // Navigate to Login screen
            navController.navigate(R.id.loginFragment);
        }


    }
/*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
            Toast.makeText(this, "no user signed in", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(this, R.id.loginFragment);
            //navController.navigate(R.id.loginFragment);
        }
    }
*/
}