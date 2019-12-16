package com.endows.app.helper;

import android.content.Context;

import com.endows.app.models.app.EmailTemplateDetails;
import com.endows.app.models.app.Errors;
import com.endows.app.models.db.BeneficiaryDetail;
import com.endows.app.models.db.CardDetails;
import com.endows.app.models.db.Customers;
import com.endows.app.models.db.TransactionHistory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CommonHelper {
    public static String generateOTP() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }
    public static int getStringAsInt(String strVal) {
        try {
            return Integer.parseInt(strVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static TransactionHistory getTransactionHistoryObj(boolean isDebit, boolean isCredit, String from, String to, String amount) {
        TransactionHistory tnxHist = new TransactionHistory();
        if (isDebit) {
            tnxHist.setIsDebit("Y");
        }
        if (isCredit) {
            tnxHist.setIsCredit("Y");
        }
        tnxHist.setAmount(amount);
        if (!to.isEmpty()) {
            tnxHist.setTo(to);
        }
        if (!from.isEmpty()) {
            tnxHist.setFrom(from);
        }
        tnxHist.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        return tnxHist;
    }
    public static boolean sendTransactionEmailToCustomer(Context context,List<CardDetails> cardDetailsLst,String firstName,String tnxAmt,String emailId,int cardtype,boolean isDebit) {
        //Add a transaction history
        TransactionHistory transHist = null;
        if(isDebit) {
            transHist = CommonHelper.getTransactionHistoryObj(true, false, "", firstName, tnxAmt);
        } else {
            transHist = CommonHelper.getTransactionHistoryObj(false, true, "", firstName, tnxAmt);
        }

        for(CardDetails card : cardDetailsLst) {
            if(cardtype == (card.getCardType())) {
                //Send a transaction email to the customer
                EmailTemplateDetails emailTemplate = new EmailTemplateDetails("transaction.html",emailId,null,
                        false,false,true,false,card,transHist,null);
                EmailHelper emailHelper = new EmailHelper(context,emailTemplate);
                emailHelper.execute("");
                return true;
            }
        }
        return false;
    }
    public static Errors isPayeeAdditionValid(Customers customerObj,String receiverEmailId) {
        if(customerObj != null) {
            if(receiverEmailId.equalsIgnoreCase(customerObj.getEmailId())) {
                return new Errors("","Invalid Email","Self email addition not valid");
            }
            if(customerObj.getBeneficiaryDetails() != null) {
                for (BeneficiaryDetail beneficiaryDetail : customerObj.getBeneficiaryDetails()) {
                    if (receiverEmailId.equalsIgnoreCase(beneficiaryDetail.getBeneficiaryEmailId())) {
                        return new Errors("", "Duplicate Email found", "Email id already exists in the payee list");
                    }
                }
            }
        }
        return null;
    }
}
