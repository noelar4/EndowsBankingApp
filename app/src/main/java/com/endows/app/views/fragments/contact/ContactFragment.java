package com.endows.app.views.fragments.contact;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallClicked(view);
            }
        });

        view.findViewById(R.id.sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSMSClicked(view);
            }
        });

        view.findViewById(R.id.email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEmailClicked(view);
            }
        });

    }

    public void onCallClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:6479066536"));
        startActivity(intent);
    }

    public void onEmailClicked(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","endowsapp@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Endows Client concern");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public void onSMSClicked(View view) {
        Uri uri = Uri.parse("smsto:6479066536");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("Endows APP", "Endows");
        startActivity(intent);
    }



}
