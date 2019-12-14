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
        AssetManager assetManager = getApplicationContext().getResources().getAssets();
        try {
            InputStream inputStream = assetManager.open("credit_email_template.html");
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            String htmlMsg = new String(b);
            new EmailHelper().execute("vibin2joby@gmail.com",htmlMsg);

            inputStream.close();

            /*EncryptPasswords td= new EncryptPasswords();

            String target="3334e8c4c6ffe45eab41076b2675fe59";
            String encrypted=td.encrypt(target);
            String decrypted=td.decrypt(encrypted);

            System.out.println("String To Encrypt: "+ target);
            System.out.println("Encrypted String:" + encrypted);
            System.out.println("Decrypted String:" + decrypted);*/
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
