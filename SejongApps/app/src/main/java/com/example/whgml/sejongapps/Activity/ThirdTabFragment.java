package com.example.whgml.sejongapps.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whgml.sejongapps.R;


public class ThirdTabFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mParam1;
    public ThirdTabFragment() {
        // Required empty public constructor
    }

    public static ThirdTabFragment newInstance(int section_number) {
        ThirdTabFragment fragment = new ThirdTabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_tab, container, false);
    }
}
