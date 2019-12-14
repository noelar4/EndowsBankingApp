package com.endows.app.serviceimpl;

import android.content.Context;

import com.endows.app.callbacks.FirebaseCallback;
import com.endows.app.callbacks.LoginCallback;
import com.endows.app.helper.EmailHelper;
import com.endows.app.helper.SMSHelper;
import com.endows.app.helper.CommonHelper;
import com.endows.app.models.app.EmailTemplateDetails;
import com.endows.app.models.app.Errors;
import com.endows.app.models.app.LoginResponse;
import com.endows.app.models.db.Customers;
import com.endows.app.service.FirebaseService;
import com.endows.app.service.LoginService;

public class LoginServiceImpl implements LoginService {

    @Override
    public void generateSmsVerificationCode(final LoginCallback callback, String phoneNumber) {
        final String otp = CommonHelper.generateOTP();
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        firebaseService.getCustDetailsUsingPhoneNumber(new FirebaseCallback() {
            @Override
            public void onCallbackCustomerDetails(Customers custObj) {
                LoginResponse response = new LoginResponse();
                try {
                    if (custObj != null) {
                        firebaseService.saveVerificationCode(custObj.getCustomerId(), otp);
                        response.setResponseMsg("Verification code sent and saved in DB successfully");
                        response.setSuccess(true);
                    } else {
                        response.setResponseMsg("Customer node is empty");
                        response.setSuccess(false);
                    }
                    callback.onLoginCallback(response);
                } catch (Exception e) {
                    response.setErrResponse(new Errors("",e.getMessage(),e.getCause().toString()));
                }
            }
        },phoneNumber);

        //Send otp to user and save it in DB
        SMSHelper.sendMessage(otp,phoneNumber);
    }

    @Override
    public void validateUserSignIn(final LoginCallback callback, String cardnumber, final String password) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final LoginResponse response = new LoginResponse();
        try {
            firebaseService.getCustDetailsUsingCardNumber(new FirebaseCallback() {
                @Override
                public void onCallbackCustomerDetails(Customers custObj) {
                    if (custObj != null && password.equals(custObj.getMobileAppPassword())) {
                        response.setSuccess(true);
                    } else {
                        response.setResponseMsg("Customer node is empty");
                        response.setSuccess(false);
                    }
                    callback.onLoginCallback(response);
                }
            },cardnumber);
        } catch(Exception e) {
            response.setErrResponse(new Errors("",e.getMessage(),e.getCause().toString()));
        }
    }

    @Override
    public void generateEmailVerificationCode(final LoginCallback callback, String emailId, Context context) {
        final String verificationCode = CommonHelper.generateOTP();
        EmailTemplateDetails emailTemplate = new EmailTemplateDetails("verification_code_template.html",emailId,verificationCode,
                false,true,false);
        EmailHelper emailHelper = new EmailHelper(context,emailTemplate);


        final FirebaseService firebaseService = new FirebaseServiceImpl();
        firebaseService.getCustDetailsUsingEmailId(new FirebaseCallback() {
            @Override
            public void onCallbackCustomerDetails(Customers custObj) {
                LoginResponse response = new LoginResponse();
                if (custObj != null) {
                    firebaseService.saveVerificationCode(custObj.getCustomerId(), verificationCode);
                    response.setResponseMsg("Verification code sent and saved in DB successfully");
                    response.setSuccess(true);
                } else {
                    response.setResponseMsg("Customer node is empty");
                    response.setSuccess(false);
                }
                callback.onLoginCallback(response);
            }
        },emailId);
        // Sending email to the customer with the verification code
        emailHelper.execute("");
    }

    @Override
    public void validateVerificationCode(final LoginCallback callback, final String code, String custId) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final LoginResponse response = new LoginResponse();
        try {
            firebaseService.getCustDetailsUsingCustomerId(new FirebaseCallback() {
                @Override
                public void onCallbackCustomerDetails(Customers custObj) {
                    if (custObj != null && code.equals(custObj.getVerificationCode())) {
                        response.setResponseMsg("Verification code is same");
                        response.setSuccess(true);
                    } else {
                        response.setResponseMsg("Customer node is empty");
                        response.setSuccess(false);
                    }
                    callback.onLoginCallback(response);
                }
            }, custId);
        } catch(Exception e) {
            response.setErrResponse(new Errors("",e.getMessage(),e.getCause().toString()));
        }
    }

    @Override
    public void saveNewPassword(LoginCallback callback, String custId, String password) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        firebaseService.savePassword(custId,password);
    }
}
