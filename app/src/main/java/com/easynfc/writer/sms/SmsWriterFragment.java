package com.easynfc.writer.sms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsWriterFragment extends Fragment implements SmsWriterContract.View {


    public static final String TAG = "SmsWriterFragment";

    public SmsWriterContract.Presenter presenter;

    public SmsWriterFragment() {
        // Required empty public constructor
    }

    public static SmsWriterFragment newInstance() {
        return new SmsWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_writer_sms, container, false);
        return v;
    }

    @Override
    public void setPresenter(SmsWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void OnTagWritten() {

    }
}
