package com.endows.app.models.app;

public class LoginResponse {
    private boolean isSuccess;
    private String responseMsg;
    private Errors errResponse;

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

    @Override
    public String toString() {
        return "LoginResponse{" +
                "isSuccess=" + isSuccess +
                ", responseMsg='" + responseMsg + '\'' +
                ", errResponse=" + errResponse +
                '}';
    }
}
