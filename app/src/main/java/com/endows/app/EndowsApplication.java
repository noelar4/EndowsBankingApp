package com.endows.app;

import android.app.Application;

import com.endows.app.models.db.Customers;

public class EndowsApplication extends Application {

    private Customers customers;

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
}
