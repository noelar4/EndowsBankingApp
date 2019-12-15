package com.endows.app.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.endows.app.R;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        NavController navController = Navigation.findNavController(this, R.id.nav_graph);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }
}
