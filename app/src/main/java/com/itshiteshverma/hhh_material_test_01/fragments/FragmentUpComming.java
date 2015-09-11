package com.itshiteshverma.hhh_material_test_01.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itshiteshverma.hhh_material_test_01.R;

public class FragmentUpComming extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fragment_up_comming, container, false);

        return rootView;
    }

}