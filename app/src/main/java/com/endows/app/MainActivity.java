package com.endows.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

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
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        FirebaseServiceImpl impl = new FirebaseServiceImpl();
        impl.getCustDetailsUsingEmailId(new FirebaseCallback() {
            @Override
            public void onCallbackCustomerDetails(Customers custObj) {
                System.out.println(custObj);
            }

        },"xx@xx.com");
        String prompt = "";
        AssetManager assetManager = getApplicationContext().getResources().getAssets();
        try {
            InputStream inputStream = assetManager.open("credit_email_template.html");
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
