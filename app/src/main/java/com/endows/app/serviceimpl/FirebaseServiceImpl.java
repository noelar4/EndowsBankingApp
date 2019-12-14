package com.endows.app.serviceimpl;

import androidx.annotation.NonNull;

import com.endows.app.models.app.Errors;
import com.endows.app.models.app.FirebaseResponse;
import com.endows.app.models.db.AccountDetails;
import com.endows.app.models.db.BeneficiaryDetail;
import com.endows.app.models.db.CardDetails;
import com.endows.app.models.db.Customers;
import com.endows.app.models.db.TransactionHistory;
import com.endows.app.callbacks.FirebaseCallback;
import com.endows.app.service.FirebaseService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseServiceImpl implements FirebaseService {
    private DatabaseReference myRef = null;

    private void initDataBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public FirebaseServiceImpl() {
        initDataBase();
    }

    @Override
    public void getCustDetailsUsingCustomerId(final FirebaseCallback firebaseCallback, String customerId) {
        try {
            myRef.child("Customers").child(customerId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Gson gson = new Gson();
                    Customers custObj = gson.fromJson(snapshot.getValue().toString(), Customers.class);
                    firebaseCallback.onCallbackCustomerDetails(custObj);
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
                    Gson gson = new Gson();

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Customers cust = gson.fromJson(snap.getValue().toString(), Customers.class);
                        custMap.put(snap.getKey(), cust);
                    }

                    for (String keys : custMap.keySet()) {
                        if (custMap.get(keys).getCardDetails() != null) {
                            for (CardDetails cardDetails : custMap.get(keys).getCardDetails()) {
                                if (debitCardNumber.equalsIgnoreCase(cardDetails.getCardNumber())) {
                                    Customers custObj = custMap.get(keys);
                                    firebaseCallback.onCallbackCustomerDetails(custObj);
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
                    Gson gson = new Gson();

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Customers cust = gson.fromJson(snap.getValue().toString(), Customers.class);
                        custMap.put(snap.getKey(), cust);
                    }

                    for (String keys : custMap.keySet()) {
                        if (phoneNumber.equalsIgnoreCase(custMap.get(keys).getPhoneNumber())) {
                            Customers custObj = custMap.get(keys);
                            firebaseCallback.onCallbackCustomerDetails(custObj);
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
                    Gson gson = new Gson();

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Customers cust = gson.fromJson(snap.getValue().toString(), Customers.class);
                        custMap.put(snap.getKey(), cust);
                    }

                    for (String keys : custMap.keySet()) {
                        if (emailId.equalsIgnoreCase(custMap.get(keys).getEmailId())) {
                            Customers custObj = custMap.get(keys);
                            firebaseCallback.onCallbackCustomerDetails(custObj);
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
    public void addNewTransactionForCreditCard(String custId, final TransactionHistory transHist) {
        try {
            myRef.child("Customers").child(custId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Gson gson = new Gson();
                    Customers customerObj = gson.fromJson(snapshot.getValue().toString(), Customers.class);

                    // card type = 1 --> credit card
                    for (CardDetails cardDetails : customerObj.getCardDetails()) {
                        if (cardDetails.getCardType().equals("1")) {
                            if (cardDetails.getTransactionHistory() != null) {
                                cardDetails.getTransactionHistory().add(transHist);
                            } else {
                                List<TransactionHistory> transList = new ArrayList<>();
                                transList.add(transHist);
                                cardDetails.setTransactionHistory(transList);
                            }
                        }
                    }

                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put("Customers/" + customerObj.getCustomerId(), customerObj);
                    myRef.updateChildren(updateMap);
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
    public void updateBalance(String custId, final String accountType, final String newBalance, final TransactionHistory transHist) {
        final Map<String, Customers> custMap = new HashMap<>();
        try {
            myRef.child("Customers").child(custId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Gson gson = new Gson();
                    Customers customerObj = gson.fromJson(snapshot.getValue().toString(), Customers.class);

                    // account type = 1 --> chequing
                    // account type = 2 --> savings
                    for (AccountDetails acctDetails : customerObj.getAccountDetails()) {
                        if (acctDetails.getAccountType().equalsIgnoreCase(accountType)) {
                            acctDetails.setAccountBalance(newBalance);
                            if (acctDetails.getTransactionHistory() != null) {
                                acctDetails.getTransactionHistory().add(transHist);
                            } else {
                                List<TransactionHistory> transList = new ArrayList<>();
                                transList.add(transHist);
                                acctDetails.setTransactionHistory(transList);
                            }
                        }


                        Map<String, Object> updateMap = new HashMap<>();
                        updateMap.put("Customers/" + customerObj.getCustomerId(), customerObj);
                        myRef.updateChildren(updateMap);
                    }
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
    public void addNewPayee(final FirebaseCallback firebaseCallback, String custId, String payeeName, String payeeEmailId) {
        try {
            final BeneficiaryDetail payeeObj = new BeneficiaryDetail();
            payeeObj.setBeneficiaryEmailId(payeeEmailId);
            payeeObj.setBeneficiaryName(payeeName);
            myRef.child("Customers").child(custId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Gson gson = new Gson();
                    Customers customerObj = gson.fromJson(snapshot.getValue().toString(), Customers.class);

                    if (customerObj.getBeneficiaryDetails() != null) {
                        customerObj.getBeneficiaryDetails().add(payeeObj);
                    } else {
                        List<BeneficiaryDetail> payeeLst = new ArrayList<>();
                        customerObj.setBeneficiaryDetails(payeeLst);
                    }

                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put("Customers/" + customerObj.getCustomerId(), customerObj);
                    myRef.updateChildren(updateMap);
                    firebaseCallback.onCallbackCustomerDetails(customerObj);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
