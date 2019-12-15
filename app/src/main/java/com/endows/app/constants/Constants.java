package com.endows.app.constants;

public interface Constants {
    interface ErrorConstants {
        String E_001_CODE = "001";
        String E_001_MESSAGE = "Customer ID not found";
        String E_001_DESCRIPTION = "Customer node is empty in the DB";

        String E_002_CODE = "002";
        String E_002_MESSAGE = "Operation failed";
        String E_002_DESCRIPTION = "Insufficient balance in chequing account";

        String E_003_CODE = "003";
        String E_003_MESSAGE = "Operation failed";
        String E_003_DESCRIPTION = "Insufficient balance in savings account";

        String E_004_CODE = "004";
        String E_004_MESSAGE = "Invalid payee email Id";
        String E_004_DESCRIPTION = "No such email exists in the Bank database";

        String E_005_CODE = "005";
        String E_005_MESSAGE = "Payment failure";
        String E_005_DESCRIPTION = "Payment failed for credit card";

        String E_006_CODE = "006";
        String E_006_MESSAGE = "Invalid Email";
        String E_006_DESCRIPTION = "Incorrect Receiver email id";

        String E_007_CODE = "007";
        String E_007_MESSAGE = "Invalid Phone number";
        String E_007_DESCRIPTION = "No Account exists with this phone number";

        String E_008_CODE = "008";
        String E_008_MESSAGE = "Invalid Card number";
        String E_008_DESCRIPTION = "No Account exists with this Card number";

        String E_009_CODE = "009";
        String E_009_MESSAGE = "Invalid Email ID";
        String E_009_DESCRIPTION = "No Account exists with this Email Id";

        String E_010_CODE = "010";
        String E_010_MESSAGE = "Invalid verification code";
        String E_010_DESCRIPTION = "Incorrect Verification code";
    }

    interface TransactionConstants {
        String CHEQUING_ACCOUNT = "1";
        String SAVINGS_ACCOUNT = "2";
    }
}
