package com.easynfc.mytags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTagsFragment extends Fragment {


    public static final String TAG = "MyTagsFragment";

    public MyTagsFragment() {
        // Required empty public constructor
    }

    public static MyTagsFragment newInstance() {
        return new MyTagsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_tags, container, false);
    }


}
