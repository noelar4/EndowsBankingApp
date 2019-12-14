package com.endows.app.service;

import com.endows.app.callbacks.TransactionCallback;
import com.endows.app.models.app.TransactionResponse;

public interface TransactionService {
    // Transfer between accounts
    void transferBetweenAccounts(TransactionCallback callback,String custId, String fromAcct, String toAcct, String Amount);
    // Interac
    void interacMoneyTransfer(TransactionCallback callback,String custId,String receiverEmailId,String amount);
    // Adding Payee
    void addBeneficiary(TransactionCallback callback,String custId,String payeeName,String payeeEmailId);
    // Pay utility bills
    void payUtilityBills(TransactionCallback callback,String custId, String payAmt,boolean isPayFromCredit);
}
