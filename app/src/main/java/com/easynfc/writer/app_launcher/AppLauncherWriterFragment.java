package com.easynfc.writer.app_launcher;


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
public class AppLauncherWriterFragment extends BaseTypeFragment implements AppLauncherWriterContract.View {


    public static final String TAG = "AppLauncherWriterFragment";

    public AppLauncherWriterContract.Presenter presenter;

    public AppLauncherWriterFragment() {
        // Required empty public constructor
    }

    public static AppLauncherWriterFragment newInstance() {
        return new AppLauncherWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_writer_app_launcher, container, false);
        return v;
    }

    @Override
    public void setPresenter(AppLauncherWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void OnTagWritten() {
        super.tagWritten();
    }
}
