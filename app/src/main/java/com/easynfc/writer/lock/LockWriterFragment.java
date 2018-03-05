package com.easynfc.writer.lock;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easynfc.R;
import com.easynfc.util.AppUtils;
import com.easynfc.writer.BaseTypeFragment;
import com.easynfc.writer.url.UrlWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterFragment;

import org.w3c.dom.Text;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LockWriterFragment extends BaseTypeFragment implements LockWriterContract.View {


    public static final String TAG = "LockWriterFragment";

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_lock_title)
    TextView txtLockTitle;
    @BindView(R.id.txt_lock_subtitle)
    TextView txtSubtitle;
    public LockWriterContract.Presenter presenter;
    @BindView(R.id.btn_lock)
    Button btnLock;

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
        View v = inflater.inflate(R.layout.fragment_writer_lock, container, false);
        ButterKnife.bind(this, v);
        setParentView(v);
        Typeface typeface = AppUtils.getAppTypeface(getContext());
        txtTitle.setTypeface(typeface);
        txtLockTitle.setTypeface(typeface);
        txtSubtitle.setTypeface(typeface);
        btnLock.setTypeface(typeface);
        return v;
    }

    @OnClick(R.id.btn_lock)
    void onRecordTagBtnPressed() {
        presenter.enableForegroundDispatch();
        super.showDialog();
    }


    @Override
    public void setPresenter(LockWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onTagWritten() {
        super.tagWritten();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.disableForegroundDispatch();
    }

    @Override
    public void processNfc(Intent intent) {
        super.processNfc(intent);
        presenter.lockTag(intent);
    }


}
