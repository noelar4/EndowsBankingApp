package com.endows.app.callbacks;

import com.endows.app.models.app.LoginResponse;

public interface LoginCallback {
    void onLoginCallback(LoginResponse response);
}
