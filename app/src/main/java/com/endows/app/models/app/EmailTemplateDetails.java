package com.endows.app.models.app;

import com.endows.app.models.db.BeneficiaryDetail;
import com.endows.app.models.db.CardDetails;
import com.endows.app.models.db.TransactionHistory;

public class EmailTemplateDetails {
    private String templateName;
    private String senderEmailId;
    private String verificationCode;
    private boolean isCardTemplate;
    private boolean isOtpTemplate;
    private boolean isTransactionTemplate;
    private boolean isBeneficiaryTemplate;
    private CardDetails cardDetails;
    private TransactionHistory transactionHistory;
    private BeneficiaryDetail beneficiaryDetail;

    public EmailTemplateDetails(String templateName, String senderEmailId, String verificationCode, boolean isCardTemplate,
                                boolean isOtpTemplate, boolean isTransactionTemplate, boolean isBeneficiaryTemplate, CardDetails cardDetails,
                                TransactionHistory transactionHistory, BeneficiaryDetail beneficiaryDetail) {
        this.templateName = templateName;
        this.senderEmailId = senderEmailId;
        this.verificationCode = verificationCode;
        this.isCardTemplate = isCardTemplate;
        this.isOtpTemplate = isOtpTemplate;
        this.isTransactionTemplate = isTransactionTemplate;
        this.isBeneficiaryTemplate = isBeneficiaryTemplate;
        this.cardDetails = cardDetails;
        this.transactionHistory = transactionHistory;
        this.beneficiaryDetail = beneficiaryDetail;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getSenderEmailId() {
        return senderEmailId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public boolean isCardTemplate() {
        return isCardTemplate;
    }

    public boolean isOtpTemplate() {
        return isOtpTemplate;
    }

    public boolean isTransactionTemplate() {
        return isTransactionTemplate;
    }

    public CardDetails getCardDetails() {
        return cardDetails;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    public boolean isBeneficiaryTemplate() {
        return isBeneficiaryTemplate;
    }

    public BeneficiaryDetail getBeneficiaryDetail() {
        return beneficiaryDetail;
    }
}
