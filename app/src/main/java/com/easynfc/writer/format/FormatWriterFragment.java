package com.easynfc.writer.format;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easynfc.R;
import com.easynfc.util.AppUtils;
import com.easynfc.writer.BaseTypeFragment;
import com.easynfc.writer.lock.LockWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterFragment;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormatWriterFragment extends BaseTypeFragment implements FormatWriterContract.View {


    public static final String TAG = "FormatWriterFragment";

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_format_title)
    TextView txtFormatTitle;
    @BindView(R.id.txt_format_subtitle)
    TextView txtSubtitle;
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
        ButterKnife.bind(this, v);
        setParentView(v);
        Typeface typeface = AppUtils.getAppTypeface(getContext());
        txtTitle.setTypeface(typeface);
        txtFormatTitle.setTypeface(typeface);
        txtSubtitle.setTypeface(typeface);
        return v;
    }

    @OnClick(R.id.btn_format)
    void onRecordTagBtnPressed() {
        presenter.enableForegroundDispatch();
        super.showDialog();
    }

    @Override
    public void setPresenter(FormatWriterContract.Presenter presenter) {
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
        presenter.formatTag(intent);
    }

}
