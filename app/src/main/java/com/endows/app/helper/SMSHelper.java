package com.endows.app.helper;

import android.util.Base64;
import android.util.Log;

import com.endows.app.service.TwilioService;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SMSHelper{
    public static final String ACCOUNT_SID = "AC2380d9936869c08c9a920594ca916ee2";
    public static final String AUTH_TOKEN = "3334e8c4c6ffe45eab41076b2675fe59";

    public static void sendMessage(String otp, String receiver) {
        String body = "Your One time password for Endows app login verification is "+ otp;
        String from = "+12562428523";

        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP
        );

        Map<String, String> data = new HashMap<>();
        data.put("From", from);
        data.put("To", receiver);
        data.put("Body", body);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twilio.com/2010-04-01/")
                .build();
        TwilioService api = retrofit.create(TwilioService.class);

        api.sendMessage(ACCOUNT_SID, base64EncodedCredentials, data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) Log.d("TAG", "onResponse->success");
                else Log.d("TAG", "onResponse->failure");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", "onFailure");
            }
        });
    }
}
