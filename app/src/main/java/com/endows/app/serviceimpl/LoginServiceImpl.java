package com.endows.app.serviceimpl;

import android.content.Context;

import com.endows.app.callbacks.FirebaseCallback;
import com.endows.app.callbacks.LoginCallback;
import com.endows.app.constants.Constants;
import com.endows.app.helper.EmailHelper;
import com.endows.app.helper.SMSHelper;
import com.endows.app.helper.CommonHelper;
import com.endows.app.models.app.EmailTemplateDetails;
import com.endows.app.models.app.Errors;
import com.endows.app.models.app.FirebaseResponse;
import com.endows.app.models.app.LoginResponse;
import com.endows.app.models.db.Customers;
import com.endows.app.service.FirebaseService;
import com.endows.app.service.LoginService;

public class LoginServiceImpl implements LoginService, Constants.ErrorConstants {

    @Override
    public void generateSmsVerificationCode(final LoginCallback callback, final String phoneNumber) {
        final String otp = CommonHelper.generateOTP();
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final LoginResponse response = new LoginResponse();
        firebaseService.getCustDetailsUsingPhoneNumber(new FirebaseCallback() {
            @Override
            public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                try {
                    if (firebaseResponse.getCustomerObj() != null) {
                        //Send otp to user and save it in DB
                        SMSHelper.sendMessage(otp,phoneNumber);
                        firebaseService.saveVerificationCode(firebaseResponse.getCustomerObj().getCustomerId(), otp);
                        firebaseResponse.getCustomerObj().setVerificationCode(otp);
                        response.setResponseMsg("Verification code sent and saved in DB successfully");
                        response.setCustomerObj(firebaseResponse.getCustomerObj());
                        response.setSuccess(true);
                    } else {
                        response.setErrResponse(new Errors(E_007_CODE,E_007_MESSAGE,E_007_DESCRIPTION));
                        response.setSuccess(false);
                    }
                    callback.onLoginCallback(response);
                } catch (Exception e) {
                    response.setErrResponse(new Errors("",e.getMessage(),e.getCause().toString()));
                }
            }
        },phoneNumber);
    }

    @Override
    public void validateUserSignIn(final LoginCallback callback, String cardnumber, final String password) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final LoginResponse response = new LoginResponse();
        try {
            firebaseService.getCustDetailsUsingCardNumber(new FirebaseCallback() {
                @Override
                public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                    if (firebaseResponse.getCustomerObj() != null && password.equals(firebaseResponse.getCustomerObj().getMobileAppPassword())) {
                        response.setSuccess(true);
                        response.setCustomerObj(firebaseResponse.getCustomerObj());
                    } else {
                        response.setErrResponse(new Errors(E_008_CODE,E_008_MESSAGE,E_008_DESCRIPTION));
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
                false,true,false,false,null,null,null);
        final EmailHelper emailHelper = new EmailHelper(context,emailTemplate);


        final FirebaseService firebaseService = new FirebaseServiceImpl();
        firebaseService.getCustDetailsUsingEmailId(new FirebaseCallback() {
            @Override
            public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                LoginResponse response = new LoginResponse();
                if (firebaseResponse.getCustomerObj() != null) {
                    firebaseResponse.getCustomerObj().setVerificationCode(verificationCode);
                    firebaseService.saveVerificationCode(firebaseResponse.getCustomerObj().getCustomerId(), verificationCode);
                    // Sending email to the customer with the verification code
                    emailHelper.execute("");
                    response.setResponseMsg("Verification code sent and saved in DB successfully");
                    response.setSuccess(true);
                    response.setCustomerObj(firebaseResponse.getCustomerObj());
                } else {
                    response.setErrResponse(new Errors(E_009_CODE,E_009_MESSAGE,E_009_DESCRIPTION));
                    response.setSuccess(false);
                }
                callback.onLoginCallback(response);
            }
        },emailId);
    }

    @Override
    public void validateVerificationCode(final LoginCallback callback, final String code, String custId) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final LoginResponse response = new LoginResponse();
        try {
            firebaseService.getCustDetailsUsingCustomerId(new FirebaseCallback() {
                @Override
                public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                    if (firebaseResponse.getCustomerObj() != null) {
                        if (code.equals(firebaseResponse.getCustomerObj().getVerificationCode())) {
                            response.setResponseMsg("Verification code is same");
                            response.setSuccess(true);
                            response.setCustomerObj(firebaseResponse.getCustomerObj());
                        } else {
                            response.setSuccess(false);
                            response.setErrResponse(new Errors(E_010_CODE,E_010_MESSAGE,E_010_DESCRIPTION));
                        }

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
    public LoginResponse saveNewPassword( String custId, String password) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        LoginResponse response = new LoginResponse();
        FirebaseResponse firebaseResponse = firebaseService.savePassword(custId,password);
        if(firebaseResponse.isSuccess()) {
            response.setSuccess(true);
            response.setCustomerObj(firebaseResponse.getCustomerObj());
            response.setResponseMsg(firebaseResponse.getMessage());
        } else {
            response.setErrResponse(firebaseResponse.getErrors());
        }
        return response;
    }
}
