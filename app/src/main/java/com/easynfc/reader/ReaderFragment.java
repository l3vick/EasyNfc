package com.easynfc.reader;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.BaseFragment;
import com.easynfc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReaderFragment extends BaseFragment {


    public static final String TAG = "ReaderFragment";

    public ReaderFragment() {
        // Required empty public constructor
    }

    public static ReaderFragment newInstance(){
        return new ReaderFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reader, container, false);
    }

}
