package com.endows.app.views.fragments;

import android.os.Bundle;
import android.view.Menu;

import com.endows.app.R;
import com.endows.app.callbacks.TransactionCallback;
import com.endows.app.constants.Constants;
import com.endows.app.models.app.TransactionResponse;
import com.endows.app.service.TransactionService;
import com.endows.app.serviceimpl.TransactionServiceImpl;
import com.endows.app.views.fragments.home.NavigationAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomeActivity extends AppCompatActivity implements NavigationAdapter.OnItemClickListener {

    private DrawerLayout drawer;

    private NavigationAdapter navAdapter;

    private NavController navController;


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
        navAdapter.setItemClickListener(this);
        rvNavigation.setAdapter(navAdapter);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.setGraph(navController.getGraph(), getIntent().getExtras());
        NavigationUI.setupActionBarWithNavController(this, navController, drawer);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, drawer);
    }

    @Override
    public void itemClicked(String item) {
        if (item.equals(Constants.NavItems.TRANSFER_BETWEEN)) {
            navController.navigate(R.id.transferFragment);
        } else if (item.equals(Constants.NavItems.INTERAC)) {
            navController.navigate(R.id.interacFragment);
        } else if (item.equals(Constants.NavItems.PAY_BILL)) {
            navController.navigate(R.id.payBillFragment);
        } else if (item.equals(Constants.NavItems.HELP_SUPPORT)) {
            navController.navigate(R.id.privacyFragment);
        } else if (item.equals(Constants.NavItems.CONTACT_US)) {

        } else if (item.equals(Constants.NavItems.SETTING)) {

        } else if (item.equals(Constants.NavItems.SIGN_OUT)) {
            HomeActivity.this.finish();
        }

        drawer.closeDrawers();
    }
}
