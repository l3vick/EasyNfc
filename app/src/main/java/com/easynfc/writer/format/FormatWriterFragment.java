package com.easynfc.writer.format;


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
public class FormatWriterFragment extends BaseTypeFragment implements FormatWriterContract.View {


    public static final String TAG = "FormatWriterFragment";

    public FormatWriterContract.Presenter presenter;

    public FormatWriterFragment() {
        // Required empty public constructor
    }

    public static FormatWriterFragment newInstance() {
        return new FormatWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_writer_format, container, false);
        return v;
    }

    @Override
    public void setPresenter(FormatWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void OnTagWritten() {

    }
}
