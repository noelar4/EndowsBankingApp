package com.endows.app.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class MessageLiveData extends MutableLiveData<String> {

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<? super String> observer) {
        super.observe(owner, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                observer.onChanged(s);
            }
        });
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
    }
}
