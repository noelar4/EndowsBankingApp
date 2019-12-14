package com.endows.app.helper;

import com.endows.app.models.db.TransactionHistory;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public static TransactionHistory getTnxhistoryObj(boolean isDebit, boolean isCredit, String from, String to,String amount) {
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
}
