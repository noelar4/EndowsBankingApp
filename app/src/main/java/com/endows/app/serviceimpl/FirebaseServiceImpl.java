package com.endows.app.serviceimpl;

import android.content.Context;

import androidx.annotation.NonNull;

import com.endows.app.callbacks.FirebaseCallback;
import com.endows.app.constants.Constants;
import com.endows.app.helper.CommonHelper;
import com.endows.app.helper.EmailHelper;
import com.endows.app.models.app.EmailTemplateDetails;
import com.endows.app.models.app.Errors;
import com.endows.app.models.app.FirebaseResponse;
import com.endows.app.models.db.AccountDetails;
import com.endows.app.models.db.BeneficiaryDetail;
import com.endows.app.models.db.CardDetails;
import com.endows.app.models.db.CreditCardDetails;
import com.endows.app.models.db.Customers;
import com.endows.app.models.db.TransactionHistory;
import com.endows.app.service.FirebaseService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseServiceImpl implements FirebaseService, Constants {
    private DatabaseReference myRef = null;

    private void initDataBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public FirebaseServiceImpl() {
        initDataBase();
    }

    @Override
    public void getCustDetailsUsingCustomerId(final FirebaseCallback firebaseCallback, final String customerId) {
        try {
            final Map<String, Customers> custMap = new HashMap<>();
            myRef.child("Customers").child(customerId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    FirebaseResponse response = new FirebaseResponse();
                    try {
                        Customers customerObj = snapshot.getValue(Customers.class);
                        custMap.put(customerObj.getCustomerId(), customerObj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(custMap.get(customerId) != null) {
                        response.setSuccess(true);
                        response.setCustomerObj(custMap.get(customerId));
                    } else {
                        response.setSuccess(false);
                    }
                    firebaseCallback.onCallbackCustomerDetails(response);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCustDetailsUsingCardNumber(final FirebaseCallback firebaseCallback, final String debitCardNumber) {
        final Map<String, Customers> custMap = new HashMap<>();
        try {
            myRef.child("Customers").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    FirebaseResponse response = new FirebaseResponse();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        try {
                            Customers customerObj = snap.getValue(Customers.class);
                            custMap.put(customerObj.getCustomerId(), customerObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // 1 --> CREDIT CARD
                    // 2 --> DEBIT CARD
                    for (String keys : custMap.keySet()) {
                        if (custMap.get(keys)!=null && custMap.get(keys).getCardDetails() != null) {
                            for (CardDetails cardDetails : custMap.get(keys).getCardDetails()) {
                                if (debitCardNumber.equalsIgnoreCase(cardDetails.getCardNumber()) && 2 == cardDetails.getCardType()) {
                                    Customers custObj = custMap.get(keys);
                                    response.setSuccess(true);
                                    response.setCustomerObj(custObj);
                                    firebaseCallback.onCallbackCustomerDetails(response);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCustDetailsUsingPhoneNumber(final FirebaseCallback firebaseCallback, final String phoneNumber) {
        final Map<String, Customers> custMap = new HashMap<>();
        try {
            myRef.child("Customers").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    FirebaseResponse response = new FirebaseResponse();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        try {
                            Customers customerObj = snap.getValue(Customers.class);
                            custMap.put(customerObj.getCustomerId(), customerObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    for (String keys : custMap.keySet()) {
                        if (phoneNumber.equalsIgnoreCase(custMap.get(keys).getPhoneNumber())) {
                            Customers custObj = custMap.get(keys);
                            response.setSuccess(true);
                            response.setCustomerObj(custObj);
                            firebaseCallback.onCallbackCustomerDetails(response);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCustDetailsUsingEmailId(final FirebaseCallback firebaseCallback, final String emailId) {
        final Map<String, Customers> custMap = new HashMap<>();
        try {
            myRef.child("Customers").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    FirebaseResponse response = new FirebaseResponse();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        try {
                            Customers customerObj = snap.getValue(Customers.class);
                            custMap.put(customerObj.getCustomerId(), customerObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    for (String keys : custMap.keySet()) {
                        if (emailId.equalsIgnoreCase(custMap.get(keys).getEmailId())) {
                            Customers custObj = custMap.get(keys);
                            response.setSuccess(true);
                            response.setCustomerObj(custObj);
                        }
                    }
                    if(!response.isSuccess()) {
                        response.setErrors(new Errors("","Invalid Email","Invalid Email not Found"));
                    }

                    firebaseCallback.onCallbackCustomerDetails(response);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public FirebaseResponse saveVerificationCode(String custId, String verificationCode) {
        FirebaseResponse response = new FirebaseResponse();
        try {
            myRef.child("Customers").child(custId).child("verificationCode").setValue(verificationCode);
            response.setSuccess(true);
        } catch (Exception e) {
            response.setErrors(new Errors("", e.getMessage(), e.getCause().toString()));
        }
        return response;
    }

    @Override
    public FirebaseResponse savePassword(String custId, String password) {
        FirebaseResponse response = new FirebaseResponse();
        try {
            myRef.child("Customers").child(custId).child("mobileAppPassword").setValue(password);
            response.setSuccess(true);
        } catch (Exception e) {
            response.setErrors(new Errors("", e.getMessage(), e.getCause().toString()));
        }
        return response;
    }

    @Override
    public void addNewTransactionForCreditCard(final Context context, final String custId, final String payAmt, final TransactionHistory transHist) {
        try {
            myRef.child("Customers").child(custId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    FirebaseResponse response = new FirebaseResponse();
                    Customers customerObj = snapshot.getValue(Customers.class);

                    // If the credit card details node is empty initialize it
                    if (customerObj.getCreditCardDetails() == null) {
                        customerObj.setCreditCardDetails(new CreditCardDetails());
                    }
                    //Add the amount debited from credit card
                    if (customerObj.getCreditCardDetails().getDebitedAmt() != null && !customerObj.getCreditCardDetails().getDebitedAmt().isEmpty()) {
                        int debitedAmt = CommonHelper.getStringAsInt(customerObj.getCreditCardDetails().getDebitedAmt()) + CommonHelper.getStringAsInt(payAmt);
                        customerObj.getCreditCardDetails().setDebitedAmt(String.valueOf(debitedAmt));
                    } else {
                        customerObj.getCreditCardDetails().setDebitedAmt(payAmt);
                    }

                    // card type = 1 --> credit card
                    // card type = 2 --> debit card

                    //Add a new transaction record in the history section
                    if (customerObj.getCreditCardDetails().getTransactionHistory() != null) {
                        customerObj.getCreditCardDetails().getTransactionHistory().add(transHist);
                    } else {
                        List<TransactionHistory> transList = new ArrayList<>();
                        transList.add(transHist);
                        customerObj.getCreditCardDetails().setTransactionHistory(transList);
                    }

                    //Update the changes in DB
                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put("Customers/" + customerObj.getCustomerId(), customerObj);
                    myRef.updateChildren(updateMap);
                    response.setSuccess(true);

                    //Send a transaction email to the customer
                    if(!customerObj.isOptOutEmail()) {
                        boolean isEmailSent = CommonHelper.sendTransactionEmailToCustomer(context, customerObj.getCardDetails(),
                                customerObj.getFirstName(), payAmt, customerObj.getEmailId(), 1, true);
                        if (!isEmailSent) {
                            response.setSuccess(false);
                            response.setCustomerObj(customerObj);
                            response.setErrors(new Errors("", "Email not sent", "Alert !!!!! Email not sent for the transaction"));
                        }
                    }
                    //firebaseCallback.onCallbackCustomerDetails(response);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBalance(final FirebaseCallback firebaseCallback,final Context context, final boolean isDebited, final String tnxAmt, String custId, final String accountType, final String newBalance, final TransactionHistory transHist) {
        try {
            myRef.child("Customers").child(custId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    FirebaseResponse response = new FirebaseResponse();
                    Customers customerObj = snapshot.getValue(Customers.class);

                    // account type = 1 --> chequing
                    // account type = 2 --> savings
                    for (AccountDetails acctDetails : customerObj.getAccountDetails()) {
                        if (acctDetails.getAccountType().equalsIgnoreCase(accountType)) {
                            //Add the credited/debited amount in DB
                            if (isDebited) {
                                if (acctDetails.getDebitedAmt() != null && !acctDetails.getDebitedAmt().isEmpty()) {
                                    int debitedAmt = CommonHelper.getStringAsInt(acctDetails.getDebitedAmt()) + CommonHelper.getStringAsInt(tnxAmt);
                                    acctDetails.setDebitedAmt(String.valueOf(debitedAmt));
                                } else {
                                    acctDetails.setDebitedAmt(tnxAmt);
                                }
                            } else {
                                if (acctDetails.getCreditedAmt() != null && !acctDetails.getCreditedAmt().isEmpty()) {
                                    int creditedAmt = CommonHelper.getStringAsInt(acctDetails.getCreditedAmt()) + CommonHelper.getStringAsInt(tnxAmt);
                                    acctDetails.setCreditedAmt(String.valueOf(creditedAmt));
                                } else {
                                    acctDetails.setCreditedAmt(tnxAmt);
                                }
                            }
                            //Updated the new balance in DB
                            acctDetails.setAccountBalance(newBalance);

                            //Add the new transaction in the history section
                            if (acctDetails.getTransactionHistory() != null) {
                                acctDetails.getTransactionHistory().add(transHist);
                            } else {
                                List<TransactionHistory> transList = new ArrayList<>();
                                transList.add(transHist);
                                acctDetails.setTransactionHistory(transList);
                            }
                            //Send a transaction email to the customer
                            if(!customerObj.isOptOutEmail()) {
                                boolean isEmailSent = CommonHelper.sendTransactionEmailToCustomer(context, customerObj.getCardDetails(),
                                        customerObj.getFirstName(), tnxAmt, customerObj.getEmailId(), 2, isDebited);
                                if (!isEmailSent) {
                                    response.setSuccess(false);
                                    response.setCustomerObj(customerObj);
                                    response.setErrors(new Errors("", "Email not sent", "Alert !!!!! Email not sent for the transaction"));
                                }
                            }
                        }

                        Map<String, Object> updateMap = new HashMap<>();
                        updateMap.put("Customers/" + customerObj.getCustomerId(), customerObj);
                        myRef.updateChildren(updateMap);
                        response.setSuccess(true);
                        response.setCustomerObj(customerObj);

                    }
                    firebaseCallback.onCallbackCustomerDetails(response);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewPayee(final Context context, final FirebaseCallback firebaseCallback, String custId, String payeeName, final String payeeEmailId) {
        try {
            final BeneficiaryDetail payeeObj = new BeneficiaryDetail();
            payeeObj.setBeneficiaryEmailId(payeeEmailId);
            payeeObj.setBeneficiaryName(payeeName);
            myRef.child("Customers").child(custId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    FirebaseResponse response = new FirebaseResponse();
                    Customers customerObj = snapshot.getValue(Customers.class);
                    Errors isValidPayee = CommonHelper.isPayeeAdditionValid(customerObj,payeeEmailId);
                    if( isValidPayee == null) {
                        // Add the payee to the beneficiary_details node
                        if (customerObj.getBeneficiaryDetails() != null) {
                            customerObj.getBeneficiaryDetails().add(payeeObj);
                        } else {
                            List<BeneficiaryDetail> payeeLst = new ArrayList<>();
                            payeeLst.add(payeeObj);
                            customerObj.setBeneficiaryDetails(payeeLst);
                        }

                        Map<String, Object> updateMap = new HashMap<>();
                        updateMap.put("Customers/" + customerObj.getCustomerId(), customerObj);
                        myRef.updateChildren(updateMap);
                        response.setSuccess(true);
                        response.setCustomerObj(customerObj);
                        if(!customerObj.isOptOutEmail()) {
                            EmailTemplateDetails emailTemplate = new EmailTemplateDetails("add_payee.html", customerObj.getEmailId(), null,
                                    false, false, false, true, null, null, payeeObj);
                            EmailHelper emailHelper = new EmailHelper(context, emailTemplate);
                            emailHelper.execute("");
                        }
                    } else {
                        //Indicating the payee addition is invalid
                        response.setSuccess(false);
                        response.setErrors(isValidPayee);
                    }
                    firebaseCallback.onCallbackCustomerDetails(response);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makePaymentForCreditCard(final Context context, final FirebaseCallback firebaseCallback, String custId, final String payAmount) {
        try {
            myRef.child("Customers").child(custId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    FirebaseResponse response = new FirebaseResponse();
                    Customers customerObj = dataSnapshot.getValue(Customers.class);
                    if (customerObj.getCreditCardDetails() != null) {
                        // If the credit amount is present previously add the new transaction to the existing amount
                        if (customerObj.getCreditCardDetails().getCreditedAmt() != null && !customerObj.getCreditCardDetails().getCreditedAmt().isEmpty()) {
                            int creditedAmt = CommonHelper.getStringAsInt(customerObj.getCreditCardDetails().getCreditedAmt());
                            creditedAmt += CommonHelper.getStringAsInt(payAmount);
                            //Add the credited amount
                            customerObj.getCreditCardDetails().setCreditedAmt(String.valueOf(creditedAmt));
                        } else {
                            // Else set the current transaction as the new value
                            customerObj.getCreditCardDetails().setCreditedAmt(payAmount);
                        }
                    } else {
                        //If the Credit card details was never initialized then initialize it first time and set the credited amount
                        customerObj.setCreditCardDetails(new CreditCardDetails());
                        customerObj.getCreditCardDetails().setCreditedAmt(payAmount);
                    }

                    // keep the current date as last payment date
                    customerObj.getCreditCardDetails().setLastPaymentDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put("Customers/" + customerObj.getCustomerId(), customerObj);
                    myRef.updateChildren(updateMap);

                    //Send a transaction email to the customer
                    if(!customerObj.isOptOutEmail()) {
                        boolean isEmailSent = CommonHelper.sendTransactionEmailToCustomer(context, customerObj.getCardDetails(),
                                customerObj.getFirstName(), payAmount, customerObj.getEmailId(), 1, false);
                        if (!isEmailSent) {
                            response.setSuccess(false);
                            response.setCustomerObj(customerObj);
                            response.setErrors(new Errors("", "Email not sent", "Alert !!!!! Email not sent for the transaction"));
                        }
                    }
                    firebaseCallback.onCallbackCustomerDetails(response);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void optOutofEmails(Context context, FirebaseCallback firebaseCallback, String custId) {
        FirebaseResponse response = new FirebaseResponse();
        try {
            myRef.child("Customers").child(custId).child("isOptOutEmail").setValue(true);
            response.setSuccess(true);
        } catch(Exception e) {
            response.setErrors(new Errors("",e.getMessage(),e.getCause().toString()));
        }
        firebaseCallback.onCallbackCustomerDetails(response);
    }
}
