package com.endows.app.models.app;

public class Errors {
    private String errCode;
    private String errMessage;
    private String errDescription;

    public Errors(String errCode, String errMessage, String errDescription) {
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.errDescription = errDescription;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public String getErrDescription() {
        return errDescription;
    }

    @Override
    public String toString() {
        return "Errors{" +
                "errCode='" + errCode + '\'' +
                ", errMessage='" + errMessage + '\'' +
                ", errDescription='" + errDescription + '\'' +
                '}';
    }
}
