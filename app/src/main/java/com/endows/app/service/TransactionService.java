package com.endows.app.service;

import android.content.Context;

import com.endows.app.callbacks.TransactionCallback;

public interface TransactionService {
    // Transfer between accounts
    void transferBetweenAccounts(Context context, TransactionCallback callback, String custId, String fromAcct, String toAcct, String Amount);
    // Interac
    void interacMoneyTransfer(Context context,TransactionCallback callback,String custId,String receiverEmailId,String amount);
    // Adding Payee
    void addBeneficiary(Context context,TransactionCallback callback,String custId,String payeeName,String payeeEmailId);
    // Pay utility bills
    void payUtilityBills(Context context,TransactionCallback callback,String custId, String payAmt,boolean isPayFromCredit);
    // Pay credit card bill
    void payCreditCardBill(Context context,TransactionCallback callback,String custId, String payAmt);
    //Opt out of email
    void optOutOfEmails(Context context,TransactionCallback callback,String custId);
}
