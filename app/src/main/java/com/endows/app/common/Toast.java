package com.endows.app.common;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.endows.app.R;

import androidx.appcompat.widget.AppCompatTextView;

public class Toast {

    public static void makeToast(Context context, String message) {
        android.widget.Toast toast = new android.widget.Toast(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.layout_toast, null);
        AppCompatTextView tvMessage = view.findViewById(R.id.tv_toast_message);
        tvMessage.setText(message);

        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0,40);
        toast.setView(view);
        toast.show();
    }

    public static void makeSuccessToast(Context context, String message) {
        android.widget.Toast toast = new android.widget.Toast(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.layout_success, null);
        AppCompatTextView tvMessage = view.findViewById(R.id.tv_toast_message);
        tvMessage.setText(message);

        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0,40);
        toast.setView(view);
        toast.show();
    }

    public static void makeFailureToast(Context context, String message) {
        android.widget.Toast toast = new android.widget.Toast(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.layout_failure, null);
        AppCompatTextView tvMessage = view.findViewById(R.id.tv_toast_message);
        tvMessage.setText(message);

        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0,40);
        toast.setView(view);
        toast.show();
    }
}
