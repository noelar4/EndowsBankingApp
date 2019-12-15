package com.endows.app.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class BooleanLiveData extends MutableLiveData<Boolean> {

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<? super Boolean> observer) {
        super.observe(owner, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                observer.onChanged(status);
            }
        });
    }
}
