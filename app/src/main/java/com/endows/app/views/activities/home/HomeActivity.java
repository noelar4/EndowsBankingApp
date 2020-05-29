package com.endows.app.views.activities.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.endows.app.R;
import com.endows.app.common.Toast;
import com.endows.app.constants.Constants;
import com.endows.app.views.activities.AppActivity;
import com.endows.app.views.fragments.home.NavigationAdapter;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomeActivity extends AppCompatActivity implements NavigationAdapter.OnItemClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000;

    private DrawerLayout drawer;

    private NavigationAdapter navAdapter;

    private NavController navController;

    private AppCompatTextView tvName;

    private HomeActViewModel homeActViewModel;

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

        homeActViewModel = ViewModelProviders.of(this).get(HomeActViewModel.class);

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
        View header = navigationView.getHeaderView(0);
        tvName = header.findViewById(R.id.tv_nav_name);
        navigationView.bringToFront();

        checkPermission();

        homeActViewModel.getMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvName.setText(s);
            }
        });


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
            navController.navigate(R.id.action_nav_home_to_contactFragment);
        } else if (item.equals(Constants.NavItems.SIGN_OUT)) {
            goToLogin();
            HomeActivity.this.finish();
        }

        drawer.closeDrawers();
    }

    private void goToLogin() {
        Intent intent = new Intent(HomeActivity.this, AppActivity.class);
        startActivity(intent);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeToast(HomeActivity.this, "Permission is required for generating Transaction PDF");
            } else {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }
}
