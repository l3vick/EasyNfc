package com.easynfc.writer.Url;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.R;
import com.easynfc.writer.SimpleText.SimpleTextWriterContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class UrlWriterFragment extends Fragment implements UrlWriterContract.View{

    public static final String TAG = "UrlWriterFragment";
    private UrlWriterContract.Presenter presenter;

    public UrlWriterFragment() {
        // Required empty public constructor
    }


    public static UrlWriterFragment newInstance() {
        return new UrlWriterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_url_writer, container, false);
    }

    @Override
    public void setPresenter(UrlWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void OnTagWritten() {

    }


    public void processNfc(Intent intent) {
        presenter.writeTag(intent, null);
    }

}
