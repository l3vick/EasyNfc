package com.easynfc.writer.phone;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.easynfc.R;
import com.easynfc.util.AppUtils;
import com.easynfc.writer.BaseTypeFragment;
import com.easynfc.writer.sms.SmsWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneWriterFragment extends BaseTypeFragment implements PhoneWriterContract.View {


    public static final String TAG = "PhoneWriterFragment";

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_phone_title)
    TextView txtPhoneTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;

    public PhoneWriterContract.Presenter presenter;

    public PhoneWriterFragment() {
        // Required empty public constructor
    }

    public static PhoneWriterFragment newInstance() {
        return new PhoneWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_writer_phone, container, false);
        ButterKnife.bind(this,v);
        setParentView(v);
        txtTitle.setTypeface(AppUtils.getAppTypeface(getContext()));
        txtPhoneTitle.setTypeface(AppUtils.getAppTypeface(getContext()));
        etPhone.setTypeface(AppUtils.getAppTypeface(getContext()));
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
    public void setPresenter(PhoneWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void OnTagWritten() {
        super.hideDialog();
    }


    @Override
    public void onPause() {
        super.onPause();
        presenter.disableForegroundDispatch();
    }

    @Override
    public void processNfc(Intent intent) {
        super.processNfc(intent);
        presenter.writeTag(intent, etPhone.getText().toString());
    }

}