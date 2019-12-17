package com.endows.app.serviceimpl;

import android.content.Context;

import com.endows.app.callbacks.FirebaseCallback;
import com.endows.app.callbacks.TransactionCallback;
import com.endows.app.constants.Constants;
import com.endows.app.helper.CommonHelper;
import com.endows.app.models.app.Errors;
import com.endows.app.models.app.FirebaseResponse;
import com.endows.app.models.app.TransactionResponse;
import com.endows.app.models.db.AccountDetails;
import com.endows.app.models.db.Customers;
import com.endows.app.models.db.TransactionHistory;
import com.endows.app.service.FirebaseService;
import com.endows.app.service.TransactionService;

public class TransactionServiceImpl extends CommonHelper implements TransactionService, Constants {
    /*
     * 1. Check if the from account has sufficient balance to perform the transaction
     * 2. Deduct the amount in From account and add it in To account
     * 3. Update the balance in DB for both accounts
     * 4. Add transaction statements for both accounts
     * */
    @Override
    public void transferBetweenAccounts(final Context context, final TransactionCallback callback, final String custId, final String fromAcct, final String toAcct, final String amount) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final TransactionResponse transactionResponse = new TransactionResponse();
        try {
            firebaseService.getCustDetailsUsingCustomerId(new FirebaseCallback() {
                @Override
                public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                    String chequingBalance = null, savingsBalance = null;
                    if (firebaseResponse.getCustomerObj() != null) {
                        for (AccountDetails acctDetails : firebaseResponse.getCustomerObj().getAccountDetails()) {
                            if ("1".equals(acctDetails.getAccountType())) {
                                chequingBalance = acctDetails.getAccountBalance();
                            } else {
                                savingsBalance = acctDetails.getAccountBalance();
                            }
                        }
                        //From Chequing to savings
                        if (fromAcct.equals("1")) {
                            if (getStringAsInt(chequingBalance) > 0 && getStringAsInt(chequingBalance) > getStringAsInt(amount) ) {
                                int updatedChequingBal = getStringAsInt(chequingBalance) - getStringAsInt(amount);
                                final int updatedSavingsBal = getStringAsInt(savingsBalance) + getStringAsInt(amount);

                                //Update the balance in DB
                                TransactionHistory senderTnxHist = getTransactionHistoryObj(true,false,"","Savings",amount);
                                final TransactionHistory receiverTnxHist = getTransactionHistoryObj(false,true,"Chequing","",amount);
                                firebaseService.updateBalance(new FirebaseCallback() {
                                    @Override
                                    public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                                        firebaseService.updateBalance(new FirebaseCallback() {
                                            @Override
                                            public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                                                if(firebaseResponse.getErrors() != null ) {
                                                    transactionResponse.setSuccess(false);
                                                    transactionResponse.setErrResponse(firebaseResponse.getErrors());
                                                } else {
                                                    transactionResponse.setCustomerObj(firebaseResponse.getCustomerObj());
                                                    transactionResponse.setSuccess(true);
                                                }
                                                callback.onTransactionCallback(transactionResponse);
                                            }
                                        },context, false, amount, custId, TransactionConstants.SAVINGS_ACCOUNT, String.valueOf(updatedSavingsBal), receiverTnxHist);
                                    }
                                },context, true, amount, custId, TransactionConstants.CHEQUING_ACCOUNT, String.valueOf(updatedChequingBal), senderTnxHist);
                            } else {
                                transactionResponse.setSuccess(false);
                                transactionResponse.setErrResponse(new Errors(ErrorConstants.E_002_CODE, ErrorConstants.E_002_MESSAGE, ErrorConstants.E_002_DESCRIPTION));
                            }
                        } else {
                            //From savings to chequing
                            if (getStringAsInt(savingsBalance) > 0 && getStringAsInt(savingsBalance) > getStringAsInt(amount)) {
                                final int updatedSavingsBal = getStringAsInt(savingsBalance) - getStringAsInt(amount);
                                int updatedChequingBal = getStringAsInt(chequingBalance) + getStringAsInt(amount);

                                //Update the balance in DB
                                TransactionHistory senderTnxHist = getTransactionHistoryObj(true,false,"","Chequing",amount);
                                final TransactionHistory receiverTnxHist = getTransactionHistoryObj(false,true,"Savings","",amount);
                                firebaseService.updateBalance(new FirebaseCallback() {
                                    @Override
                                    public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                                        if(firebaseResponse.getCustomerObj() != null) {
                                            firebaseService.updateBalance(new FirebaseCallback() {
                                                @Override
                                                public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                                                    if(firebaseResponse.getErrors() != null ) {
                                                        transactionResponse.setSuccess(false);
                                                        transactionResponse.setErrResponse(firebaseResponse.getErrors());
                                                    } else {
                                                        transactionResponse.setCustomerObj(firebaseResponse.getCustomerObj());
                                                        transactionResponse.setSuccess(true);
                                                    }
                                                    callback.onTransactionCallback(transactionResponse);
                                                }
                                            },context, false, amount, custId, TransactionConstants.CHEQUING_ACCOUNT, String.valueOf(updatedSavingsBal), receiverTnxHist);
                                        }
                                    }
                                },context, true, amount, custId, TransactionConstants.SAVINGS_ACCOUNT, String.valueOf(updatedChequingBal), senderTnxHist);
                            } else {
                                transactionResponse.setSuccess(false);
                                transactionResponse.setErrResponse(new Errors(ErrorConstants.E_003_CODE, ErrorConstants.E_003_MESSAGE, ErrorConstants.E_003_DESCRIPTION));
                            }
                        }


                    } else {
                        transactionResponse.setErrResponse(new Errors(ErrorConstants.E_001_CODE, ErrorConstants.E_001_MESSAGE, ErrorConstants.E_001_DESCRIPTION));
                        transactionResponse.setSuccess(false);
                    }
                }
            }, custId);
        } catch (Exception e) {
            transactionResponse.setErrResponse(new Errors("", e.getMessage(), e.getCause().toString()));
        }
    }

    /*
     * 1. Check if the receiver email id present in DB
     * 2. Check if the sender has sufficient balance in the account to perform interac
     * 3. Send money to the receiver and update balances of both sender and receiver
     * 4. Add transaction statements for both sender and receiver
     * */
    @Override
    public void interacMoneyTransfer(final Context context,final TransactionCallback callback, final String custId, final String receiverEmailId, final String amount) {
        //Step-1
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final TransactionResponse transactionResponse = new TransactionResponse();
        try {
            firebaseService.getCustDetailsUsingCustomerId(new FirebaseCallback() {
                @Override
                public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                    String senderCheqBalance = null;
                    String senderName = null;
                    if (firebaseResponse.getCustomerObj() != null) {
                        for (AccountDetails acctDetails : firebaseResponse.getCustomerObj().getAccountDetails()) {
                            if ("1".equals(acctDetails.getAccountType())) {
                                senderCheqBalance = acctDetails.getAccountBalance();
                                senderName = firebaseResponse.getCustomerObj().getFirstName();
                            }
                        }
                        if (getStringAsInt(senderCheqBalance) > 0 && getStringAsInt(senderCheqBalance) > getStringAsInt(amount)) {
                            interacValidation(context,custId, firebaseService, amount, senderCheqBalance, senderName, transactionResponse,receiverEmailId);
                            if(firebaseResponse.getErrors() != null ) {
                                transactionResponse.setSuccess(false);
                                transactionResponse.setErrResponse(firebaseResponse.getErrors());
                            } else {
                                transactionResponse.setCustomerObj(firebaseResponse.getCustomerObj());
                                transactionResponse.setSuccess(true);
                            }
                        } else {
                            transactionResponse.setSuccess(false);
                            transactionResponse.setErrResponse(new Errors(ErrorConstants.E_002_CODE, ErrorConstants.E_002_MESSAGE, ErrorConstants.E_002_DESCRIPTION));
                        }

                    } else {
                        transactionResponse.setErrResponse(new Errors(ErrorConstants.E_001_CODE, ErrorConstants.E_001_MESSAGE, ErrorConstants.E_001_DESCRIPTION));
                        transactionResponse.setSuccess(false);
                    }

                    callback.onTransactionCallback(transactionResponse);
                }
            }, custId);
        } catch (Exception e) {
            transactionResponse.setErrResponse(new Errors("", e.getMessage(), e.getCause().toString()));
        }
    }

    /*
    * 1. Check if the payee email id exists
    * 2. Then add the beneficiary to account
    * */
    @Override
    public void addBeneficiary(final Context context,final TransactionCallback callback, final String custId, final String payeeName, final String payeeEmailId) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final TransactionResponse transactionResponse = new TransactionResponse();
        //Step-1
        firebaseService.getCustDetailsUsingEmailId(new FirebaseCallback() {
            @Override
            public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                //Step-2
                if(firebaseResponse.getCustomerObj() != null) {
                    firebaseService.addNewPayee(context,new FirebaseCallback() {
                        @Override
                        public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                            if(firebaseResponse.getErrors() != null ) {
                                transactionResponse.setSuccess(false);
                                transactionResponse.setErrResponse(firebaseResponse.getErrors());
                            } else {
                                transactionResponse.setCustomerObj(firebaseResponse.getCustomerObj());
                                transactionResponse.setSuccess(true);
                            }
                            callback.onTransactionCallback(transactionResponse);
                        }
                    },custId, payeeName, payeeEmailId);
                } else {
                    transactionResponse.setSuccess(false);
                    transactionResponse.setErrResponse(new Errors(ErrorConstants.E_004_CODE, ErrorConstants.E_004_MESSAGE, ErrorConstants.E_004_DESCRIPTION));
                    callback.onTransactionCallback(transactionResponse);
                }
            }
        },payeeEmailId);

    }

    @Override
    public void payUtilityBills(final Context context,final TransactionCallback callback, final String custId, final String payAmt,boolean isPayFromCredit) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final TransactionResponse transactionResponse = new TransactionResponse();

        if(isPayFromCredit) {
            firebaseService.addNewTransactionForCreditCard(context,custId, payAmt, getTransactionHistoryObj(true, false, "", "Paying Utility bills", payAmt));
        } else {
            firebaseService.getCustDetailsUsingCustomerId(new FirebaseCallback() {
                @Override
                public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                    for(AccountDetails chequingAcct : firebaseResponse.getCustomerObj().getAccountDetails()) {
                        if(chequingAcct.getAccountType().equals(TransactionConstants.CHEQUING_ACCOUNT)) {
                            TransactionHistory tnxHistObj = getTransactionHistoryObj(true, false, "", "Paying Utility bills", payAmt);
                            firebaseService.updateBalance(new FirebaseCallback() {
                                @Override
                                public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                                    if(firebaseResponse.getErrors() != null ) {
                                        transactionResponse.setSuccess(false);
                                        transactionResponse.setErrResponse(firebaseResponse.getErrors());
                                    } else {
                                        transactionResponse.setCustomerObj(firebaseResponse.getCustomerObj());
                                        transactionResponse.setSuccess(true);
                                    }
                                    callback.onTransactionCallback(transactionResponse);
                                }
                            },context, true, payAmt, custId, TransactionConstants.CHEQUING_ACCOUNT, chequingAcct.getAccountBalance(), tnxHistObj);
                        }
                    }
                }
            },custId);
        }
    }

    @Override
    public void payCreditCardBill(Context context,final TransactionCallback callback, String custId, String payAmt) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final TransactionResponse transactionResponse = new TransactionResponse();
        firebaseService.makePaymentForCreditCard(context,new FirebaseCallback() {
            @Override
            public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse) {
                if(firebaseResponse.getCustomerObj() != null) {
                    if(firebaseResponse.getErrors() != null ) {
                        transactionResponse.setSuccess(false);
                        transactionResponse.setErrResponse(firebaseResponse.getErrors());
                    } else {
                        transactionResponse.setCustomerObj(firebaseResponse.getCustomerObj());
                        transactionResponse.setSuccess(true);
                    }
                } else {
                    transactionResponse.setSuccess(false);
                    transactionResponse.setErrResponse(new Errors(ErrorConstants.E_005_CODE, ErrorConstants.E_005_MESSAGE, ErrorConstants.E_005_DESCRIPTION));
                }
                transactionResponse.setErrResponse(firebaseResponse.getErrors());
                callback.onTransactionCallback(transactionResponse);
            }
        },custId,payAmt);
    }

    @Override
    public void optOutOfEmails(Context context, final TransactionCallback callback, String custId) {
        final FirebaseService firebaseService = new FirebaseServiceImpl();
        final TransactionResponse transactionResponse = new TransactionResponse();
        firebaseService.optOutofEmails(context, new FirebaseCallback() {
            @Override
            public void onCallbackCustomerDetails(FirebaseResponse response) {
                if(!response.isSuccess()) {
                    transactionResponse.setSuccess(false);
                    transactionResponse.setErrResponse(response.getErrors());
                } else {
                    transactionResponse.setCustomerObj(response.getCustomerObj());
                    transactionResponse.setSuccess(true);
                }
                callback.onTransactionCallback(transactionResponse);
            }
        },custId);
    }

    private void interacValidation(final Context context,final String custId, final FirebaseService firebaseService, final String amount, final String senderCheqBalance, final String senderFirstName, final TransactionResponse transactionResponse,String receiverEmailId) {
        firebaseService.getCustDetailsUsingEmailId(new FirebaseCallback() {
            @Override
            public void onCallbackCustomerDetails(final FirebaseResponse firebaseResponse) {
                String receiverCheqBalance = null;
                if (firebaseResponse.getCustomerObj() != null) {
                    for (AccountDetails acctDetails : firebaseResponse.getCustomerObj().getAccountDetails()) {
                        if ("1".equals(acctDetails.getAccountType())) {
                            receiverCheqBalance = acctDetails.getAccountBalance();
                        }
                    }
                    int updatedSenderBal = getStringAsInt(senderCheqBalance) - getStringAsInt(amount);
                    int updatedReceiverBal = getStringAsInt(receiverCheqBalance) + getStringAsInt(amount);

                    //Add a transaction history
                    TransactionHistory senderTnxHist = getTransactionHistoryObj(true,false,"",firebaseResponse.getCustomerObj().getFirstName(),amount);
                    TransactionHistory receiverTnxHist = getTransactionHistoryObj(false,true,senderFirstName,"",amount);

                    //Updating the balance of sender
                    firebaseService.updateBalance(new FirebaseCallback() {
                        @Override
                        public void onCallbackCustomerDetails(FirebaseResponse firebaseResponse1) {
                            if(firebaseResponse1.getErrors() != null ) {
                                transactionResponse.setSuccess(false);
                                transactionResponse.setErrResponse(firebaseResponse1.getErrors());
                            } else {
                                transactionResponse.setCustomerObj(firebaseResponse.getCustomerObj());
                                transactionResponse.setSuccess(true);
                            }
                        }
                    },context, true, amount, custId, TransactionConstants.CHEQUING_ACCOUNT, String.valueOf(updatedSenderBal), senderTnxHist);
                    //Updating the balance of receiver
                    firebaseService.updateBalance(new FirebaseCallback() {
                        @Override
                        public void onCallbackCustomerDetails(FirebaseResponse response2) {
                            if(response2.getErrors() != null ) {
                                transactionResponse.setSuccess(false);
                                transactionResponse.setErrResponse(response2.getErrors());
                            } else {
                                transactionResponse.setCustomerObj(firebaseResponse.getCustomerObj());
                                transactionResponse.setSuccess(true);
                            }
                        }
                    }, context, false, amount, firebaseResponse.getCustomerObj().getCustomerId(), TransactionConstants.CHEQUING_ACCOUNT, String.valueOf(updatedReceiverBal), receiverTnxHist);
                } else {
                    transactionResponse.setErrResponse(new Errors(ErrorConstants.E_006_CODE, ErrorConstants.E_006_MESSAGE, ErrorConstants.E_006_DESCRIPTION));
                    transactionResponse.setSuccess(false);
                }
            }
        }, receiverEmailId);
    }
}
