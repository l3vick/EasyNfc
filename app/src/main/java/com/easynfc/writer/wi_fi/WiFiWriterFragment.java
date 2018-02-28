package com.easynfc.writer.wi_fi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.R;
import com.easynfc.writer.sms.SmsWriterContract;
import com.easynfc.writer.sms.SmsWriterFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class WiFiWriterFragment extends Fragment implements WiFiWriterContract.View {


    public static final String TAG = "WiFiWriterFragment";

    public WiFiWriterContract.Presenter presenter;

    public WiFiWriterFragment() {
        // Required empty public constructor
    }

    public static WiFiWriterFragment newInstance() {
        return new WiFiWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_writer_wi_fi, container, false);
        return v;
    }

    @Override
    public void setPresenter(WiFiWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void OnTagWritten() {

    }
}
