package com.easynfc.writer.sms;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.easynfc.R;
import com.easynfc.util.AppUtils;
import com.easynfc.writer.BaseTypeFragment;
import com.easynfc.writer.simple_text.SimpleTextWriterContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsWriterFragment extends BaseTypeFragment implements SmsWriterContract.View {


    public static final String TAG = "SmsWriterFragment";

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_sms_phone_title)
    TextView txtPhoneTitle;
    @BindView(R.id.txt_sms_text_title)
    TextView txtTextTitle;
    @BindView(R.id.et_sms_phone)
    EditText etPhone;
    @BindView(R.id.et_sms_text)
    EditText etText;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_record)
    Button btnRecord;
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
        View v = inflater.inflate(R.layout.fragment_writer_sms, container, false);
        ButterKnife.bind(this, v);
        Typeface typeface = AppUtils.getAppTypeface(getContext());
        txtTitle.setTypeface(typeface);
        txtPhoneTitle.setTypeface(typeface);
        txtTextTitle.setTypeface(typeface);
        etPhone.setTypeface(typeface);
        etText.setTypeface(typeface);
        btnSave.setTypeface(typeface);
        btnRecord.setTypeface(typeface);
        setParentView(v);
        return v;
    }

    @OnClick(R.id.btn_record)
    void onRecordTagBtnPressed() {
        presenter.enableForegroundDispatch();
        super.showDialog();
    }

    @OnClick(R.id.btn_save)
    void onSaveTagBtnPressed() {

    }

    @Override
    public void setPresenter(SmsWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void OnTagWritten() {
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
        presenter.writeTag(intent, etPhone.getText().toString(), etText.getText().toString());
    }
}
