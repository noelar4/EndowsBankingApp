package com.endows.app.service;

import android.content.Context;

import com.endows.app.callbacks.FirebaseCallback;
import com.endows.app.models.app.FirebaseResponse;
import com.endows.app.models.db.TransactionHistory;

public interface FirebaseService {
    void getCustDetailsUsingCustomerId(FirebaseCallback firebaseCallback, String customerId);
    void getCustDetailsUsingCardNumber(FirebaseCallback firebaseCallback, String debitCardNumber);
    void getCustDetailsUsingPhoneNumber(FirebaseCallback firebaseCallback, String phoneNumber);
    void getCustDetailsUsingEmailId(FirebaseCallback firebaseCallback, String emailId);
    FirebaseResponse saveVerificationCode(String custId,String verificationCode);
    FirebaseResponse savePassword(String custId,String password);
    void addNewTransactionForCreditCard(Context context,String custId, String payAmt,TransactionHistory transHist);
    void updateBalance(FirebaseCallback firebaseCallback,Context context,boolean isDebited,String tnxAmt,String custId, String accountType, String newBalance, TransactionHistory transHist);
    void addNewPayee(Context context,FirebaseCallback firebaseCallback,String custId, String payeeName, String payeeEmailId);
    void makePaymentForCreditCard(Context context, final FirebaseCallback firebaseCallback, String custId, String payAmount);
}
