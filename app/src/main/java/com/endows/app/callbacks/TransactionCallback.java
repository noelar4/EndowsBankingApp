package com.endows.app.callbacks;

import com.endows.app.models.app.TransactionResponse;

public interface TransactionCallback {
    void onTransactionCallback(TransactionResponse response);
}
