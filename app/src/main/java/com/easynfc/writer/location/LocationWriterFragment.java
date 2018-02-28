package com.easynfc.writer.location;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.R;
import com.easynfc.writer.BaseTypeFragment;
import com.easynfc.writer.wi_fi.WiFiWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationWriterFragment extends BaseTypeFragment implements LocationWriterContract.View {


    public static final String TAG = "LocationWriterFragment";

    public LocationWriterContract.Presenter presenter;

    public LocationWriterFragment() {
        // Required empty public constructor
    }

    public static LocationWriterFragment newInstance() {
        return new LocationWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_writer_location, container, false);
        return v;
    }

    @Override
    public void setPresenter(LocationWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void OnTagWritten() {

    }
}
