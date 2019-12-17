package com.endows.app.models.app;

import com.endows.app.models.db.Customers;

public class TransactionResponse {
    private boolean isSuccess;
    private String responseMsg;
    private Errors errResponse;
    private Customers customerObj;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public Errors getErrResponse() {
        return errResponse;
    }

    public void setErrResponse(Errors errResponse) {
        this.errResponse = errResponse;
    }

    public Customers getCustomerObj() {
        return customerObj;
    }

    public void setCustomerObj(Customers customerObj) {
        this.customerObj = customerObj;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "isSuccess=" + isSuccess +
                ", responseMsg='" + responseMsg + '\'' +
                ", errResponse=" + errResponse +
                ", customerObj=" + customerObj +
                '}';
    }
}
