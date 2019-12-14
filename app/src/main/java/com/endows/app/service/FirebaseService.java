package com.endows.app.service;

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
    void addNewTransactionForCreditCard(String custId, TransactionHistory transHist);
    void updateBalance(String custId, String accountType, String newBalance, TransactionHistory transHist);
    void addNewPayee(final FirebaseCallback firebaseCallback,String custId, String payeeName, String payeeEmailId);
}
