package com.endows.app.service;

import android.content.Context;

import com.endows.app.callbacks.LoginCallback;
import com.endows.app.models.app.LoginResponse;

public interface LoginService {
    // Sign in page
    void validateUserSignIn(LoginCallback callback,String cardnumber, String password);

    // Trouble signing in
    void generateSmsVerificationCode(LoginCallback callback,String phoneNumber);
    void generateEmailVerificationCode(LoginCallback callback, String emailId, Context context);
    void validateVerificationCode(LoginCallback callback,String code,String custId);

    // New Password
    LoginResponse saveNewPassword(String custId, String password);

}
