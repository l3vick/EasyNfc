package com.easynfc.writer.lock;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.R;
import com.easynfc.writer.wi_fi.WiFiWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LockWriterFragment extends Fragment  implements LockWriterContract.View {


    public static final String TAG = "LockWriterFragment";

    public LockWriterContract.Presenter presenter;

    public LockWriterFragment() {
        // Required empty public constructor
    }

    public static LockWriterFragment newInstance() {
        return new LockWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_writer_lock, container, false);
        return v;
    }

    @Override
    public void setPresenter(LockWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void OnTagWritten() {

    }
}
