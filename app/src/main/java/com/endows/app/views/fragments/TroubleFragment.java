package com.endows.app.views.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TroubleFragment extends Fragment {


    public TroubleFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trouble, container, false);
    }

}
