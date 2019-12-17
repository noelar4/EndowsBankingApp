package com.endows.app.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.endows.app.callbacks.LoginCallback;
import com.endows.app.callbacks.TransactionCallback;
import com.endows.app.models.app.LoginResponse;
import com.endows.app.models.app.TransactionResponse;
import com.endows.app.service.FirebaseService;
import com.endows.app.service.LoginService;
import com.endows.app.service.TransactionService;
import com.endows.app.serviceimpl.LoginServiceImpl;
import com.endows.app.serviceimpl.TransactionServiceImpl;
import com.google.firebase.FirebaseApp;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, AppActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 3000);
    }
}
