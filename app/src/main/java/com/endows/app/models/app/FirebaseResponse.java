package com.endows.app.models.app;

import com.endows.app.models.db.Customers;

public class FirebaseResponse {
    private Customers customerObj;
    private boolean isSuccess;
    private String message;
    private Errors errors;

    public Customers getCustomerObj() {
        return customerObj;
    }

    public void setCustomerObj(Customers customerObj) {
        this.customerObj = customerObj;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "FirebaseResponse{" +
                "customerObj=" + customerObj +
                ", isSuccess=" + isSuccess +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}
