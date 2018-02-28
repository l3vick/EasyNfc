package com.easynfc.writer.email;


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
public class EmailWriterFragment extends BaseTypeFragment implements EmailWriterContract.View {


    public static final String TAG = "EmailWriterFragment";

    public EmailWriterContract.Presenter presenter;

    public EmailWriterFragment() {
        // Required empty public constructor
    }

    public static EmailWriterFragment newInstance() {
        return new EmailWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_writer_email, container, false);
        return v;
    }

    @Override
    public void setPresenter(EmailWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void OnTagWritten() {

    }
}
