package com.endows.app.serviceimpl;

import com.endows.app.callbacks.FirebaseCallback;
import com.endows.app.callbacks.TransactionCallback;
import com.endows.app.helper.CommonHelper;
import com.endows.app.models.app.Errors;
import com.endows.app.models.app.TransactionResponse;
import com.endows.app.models.db.AccountDetails;
import com.endows.app.models.db.Customers;
import com.endows.app.models.db.TransactionHistory;
import com.endows.app.service.FirebaseService;
import com.endows.app.service.TransactionService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionServiceImpl extends CommonHelper implements TransactionService {
    /*
     * 1. Check if the from account has sufficient balance to perform the transaction
     * 2. Deduct the amount in From account and add it in To account
     * 3. Update the balance in DB for both accounts
     * 4. Add transaction statements for both accounts
     * */
    @Override
    public void transferBetweenAccounts(final TransactionCallback callback, final String custId, final String fromAcct, final String toAcct, final String amount) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final TransactionResponse response = new TransactionResponse();
        try {
            firebaseService.getCustDetailsUsingCustomerId(new FirebaseCallback() {
                @Override
                public void onCallbackCustomerDetails(Customers custObj) {
                    String chequingBalance = null, savingsBalance = null;
                    if (custObj != null) {
                        for (AccountDetails acctDetails : custObj.getAccountDetails()) {
                            if ("1".equals(acctDetails.getAccountType())) {
                                chequingBalance = acctDetails.getAccountBalance();
                            } else {
                                savingsBalance = acctDetails.getAccountBalance();
                            }
                        }
                        //From Chequing to savings
                        if (fromAcct.equals("1")) {
                            if (getStringAsInt(chequingBalance) > 0) {
                                int updatedChequingBal = getStringAsInt(chequingBalance) - getStringAsInt(amount);
                                int updatedSavingsBal = getStringAsInt(savingsBalance) + getStringAsInt(amount);

                                //Update the balance in DB
                                TransactionHistory senderTnxHist = getTnxhistoryObj(true,false,"","Savings",amount);
                                TransactionHistory receiverTnxHist = getTnxhistoryObj(false,true,"Chequing","",amount);
                                firebaseService.updateBalance(custId, "1", String.valueOf(updatedChequingBal),senderTnxHist);
                                firebaseService.updateBalance(custId, "2", String.valueOf(updatedSavingsBal),receiverTnxHist);
                                response.setSuccess(true);
                            } else {
                                response.setSuccess(false);
                                response.setErrResponse(new Errors("", "Insufficient balance in chequing account", ""));
                            }
                        } else {
                            //From savings to chequing
                            if (getStringAsInt(savingsBalance) > 0) {
                                int updatedSavingsBal = getStringAsInt(savingsBalance) - getStringAsInt(amount);
                                int updatedChequingBal = getStringAsInt(chequingBalance) + getStringAsInt(amount);

                                //Update the balance in DB
                                TransactionHistory senderTnxHist = getTnxhistoryObj(true,false,"","Chequing",amount);
                                TransactionHistory receiverTnxHist = getTnxhistoryObj(false,true,"Savings","",amount);
                                firebaseService.updateBalance(custId, "1", String.valueOf(updatedChequingBal),senderTnxHist);
                                firebaseService.updateBalance(custId, "2", String.valueOf(updatedSavingsBal),receiverTnxHist);
                                response.setSuccess(true);
                            } else {
                                response.setSuccess(false);
                                response.setErrResponse(new Errors("", "Insufficient balance in savings account", ""));
                            }
                        }


                    } else {
                        response.setResponseMsg("Customer node is empty");
                        response.setSuccess(false);
                    }
                    callback.onTransactionCallback(response);
                }
            }, custId);
        } catch (Exception e) {
            response.setErrResponse(new Errors("", e.getMessage(), e.getCause().toString()));
        }
    }

    /*
     * 1. Check if the receiver email id present in DB
     * 2. Check if the sender has sufficient balance in the account to perform interac
     * 3. Send money to the receiver and update balances of both sender and receiver
     * 4. Add transaction statements for both sender and receiver
     * */
    @Override
    public void interacMoneyTransfer(final TransactionCallback callback, final String custId, final String receiverEmailId, final String amount) {
        //Step-1
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final TransactionResponse response = new TransactionResponse();
        try {
            firebaseService.getCustDetailsUsingCustomerId(new FirebaseCallback() {
                @Override
                public void onCallbackCustomerDetails(Customers custObj) {
                    String senderCheqBalance = null;
                    String senderName = null;
                    if (custObj != null) {
                        for (AccountDetails acctDetails : custObj.getAccountDetails()) {
                            if ("1".equals(acctDetails.getAccountType())) {
                                senderCheqBalance = acctDetails.getAccountBalance();
                                senderName = custObj.getFirstName();
                            }
                        }
                        if (getStringAsInt(senderCheqBalance) > 0) {
                            interacValidation(custId, firebaseService, amount, senderCheqBalance, senderName, response,receiverEmailId);
                            response.setSuccess(true);
                        } else {
                            response.setSuccess(false);
                            response.setErrResponse(new Errors("", "Insufficient balance in Chequing account", ""));
                        }

                    } else {
                        response.setResponseMsg("Customer node is empty");
                        response.setSuccess(false);
                    }
                    callback.onTransactionCallback(response);
                }
            }, custId);
        } catch (Exception e) {
            response.setErrResponse(new Errors("", e.getMessage(), e.getCause().toString()));
        }
    }

    /*
    * 1. Check if the payee email id exists
    * 2. Then add the beneficiary to account
    * */
    @Override
    public void addBeneficiary(final TransactionCallback callback, final String custId, final String payeeName, final String payeeEmailId) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final TransactionResponse response = new TransactionResponse();
        //Step-1
        firebaseService.getCustDetailsUsingEmailId(new FirebaseCallback() {
            @Override
            public void onCallbackCustomerDetails(Customers custObj) {
                //Step-2
                if(custObj != null) {
                    firebaseService.addNewPayee(new FirebaseCallback() {
                        @Override
                        public void onCallbackCustomerDetails(Customers custObj) {
                            response.setSuccess(true);
                            callback.onTransactionCallback(response);
                        }
                    },custId, payeeName, payeeEmailId);
                } else {
                    response.setSuccess(false);
                    response.setErrResponse(new Errors("", "Invalid payee email Id", ""));
                    callback.onTransactionCallback(response);
                }
            }
        },payeeEmailId);

    }

    @Override
    public void payUtilityBills(TransactionCallback callback, String custId, String payAmt,boolean isPayFromCredit) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final TransactionResponse response = new TransactionResponse();
        firebaseService.addNewTransactionForCreditCard(custId,getTnxhistoryObj(true,false,"","Paying Utility bills",payAmt));
    }

    private void interacValidation(final String custId, final FirebaseService firebaseService, final String amount, final String senderCheqBalance, final String senderFirstName, final TransactionResponse response,String receiverEmailId) {
        firebaseService.getCustDetailsUsingEmailId(new FirebaseCallback() {
            @Override
            public void onCallbackCustomerDetails(Customers custObj) {
                String receiverCheqBalance = null;
                if (custObj != null) {
                    for (AccountDetails acctDetails : custObj.getAccountDetails()) {
                        if ("1".equals(acctDetails.getAccountType())) {
                            receiverCheqBalance = acctDetails.getAccountBalance();
                        }
                    }
                    int updatedSenderBal = getStringAsInt(senderCheqBalance) - getStringAsInt(amount);
                    int updatedReceiverBal = getStringAsInt(receiverCheqBalance) + getStringAsInt(amount);

                    //Update the balance in DB
                    TransactionHistory senderTnxHist = getTnxhistoryObj(true,false,"",custObj.getFirstName(),amount);
                    TransactionHistory receiverTnxHist = getTnxhistoryObj(false,true,senderFirstName,"",amount);

                    firebaseService.updateBalance(custId, "1", String.valueOf(updatedSenderBal), senderTnxHist);
                    firebaseService.updateBalance(custObj.getCustomerId(), "1", String.valueOf(updatedReceiverBal), receiverTnxHist);
                } else {
                    response.setErrResponse(new Errors("", "Incorrect Receiver email id", ""));
                    response.setSuccess(false);
                }
            }
        }, receiverEmailId);
    }
}
