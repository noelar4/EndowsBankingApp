package com.endows.app.views.fragments;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.endows.app.R;
import com.endows.app.views.fragments.home.NavigationAdapter;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    private NavigationAdapter navAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        drawer = findViewById(R.id.drawer_layout);

        RecyclerView rvNavigation = findViewById(R.id.rv_navigation);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvNavigation.setLayoutManager(manager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, manager.getOrientation());
        rvNavigation.addItemDecoration(decoration);
        navAdapter = new NavigationAdapter();
        rvNavigation.setAdapter(navAdapter);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawer);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, drawer);
    }
}
