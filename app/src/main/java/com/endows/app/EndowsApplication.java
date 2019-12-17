package com.endows.app;

import android.app.Application;

import com.endows.app.models.db.Customers;

public class EndowsApplication extends Application {

    private Customers customers;

    private int accountType;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}
