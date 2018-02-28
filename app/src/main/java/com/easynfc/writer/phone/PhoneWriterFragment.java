package com.easynfc.writer.phone;


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
public class PhoneWriterFragment extends BaseTypeFragment implements PhoneWriterContract.View {


    public static final String TAG = "PhoneWriterFragment";

    public PhoneWriterContract.Presenter presenter;

    public PhoneWriterFragment() {
        // Required empty public constructor
    }

    public static PhoneWriterFragment newInstance() {
        return new PhoneWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_writer_phone, container, false);
        return v;
    }

    @Override
    public void setPresenter(PhoneWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void OnTagWritten() {

    }
}
