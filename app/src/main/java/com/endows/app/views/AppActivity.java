package com.endows.app.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.endows.app.R;
import com.endows.app.callbacks.TransactionCallback;
import com.endows.app.models.app.TransactionResponse;
import com.endows.app.service.TransactionService;
import com.endows.app.serviceimpl.TransactionServiceImpl;
import com.google.firebase.FirebaseApp;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        NavController navController = Navigation.findNavController(this, R.id.nav_graph);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }
}
