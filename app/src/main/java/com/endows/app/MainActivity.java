package com.endows.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.endows.app.helper.EmailHelper;
import com.endows.app.helper.EncryptPasswords;
import com.endows.app.models.db.Customers;
import com.endows.app.callbacks.FirebaseCallback;
import com.endows.app.serviceimpl.FirebaseServiceImpl;
import com.google.firebase.FirebaseApp;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);





    }
}
