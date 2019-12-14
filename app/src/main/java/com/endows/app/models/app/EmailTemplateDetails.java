package com.endows.app.models.app;

public class EmailTemplateDetails {
    private String templateName;
    private String senderEmailId;
    private String verificationCode;
    private boolean isCardTemplate;
    private boolean isOtpTemplate;
    private boolean isTransactionTemplate;

    public EmailTemplateDetails(String templateName, String senderEmailId, String verificationCode, boolean isCardTemplate, boolean isOtpTemplate, boolean isTransactionTemplate) {
        this.templateName = templateName;
        this.senderEmailId = senderEmailId;
        this.verificationCode = verificationCode;
        this.isCardTemplate = isCardTemplate;
        this.isOtpTemplate = isOtpTemplate;
        this.isTransactionTemplate = isTransactionTemplate;
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
}
