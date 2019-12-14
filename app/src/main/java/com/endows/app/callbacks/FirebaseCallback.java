package com.endows.app.callbacks;

import com.endows.app.models.db.Customers;

public interface FirebaseCallback {
    void onCallbackCustomerDetails(Customers custObj);
}
