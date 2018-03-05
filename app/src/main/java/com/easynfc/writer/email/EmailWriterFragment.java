package com.easynfc.writer.email;


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
import com.easynfc.writer.phone.PhoneWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailWriterFragment extends BaseTypeFragment implements EmailWriterContract.View {


    public static final String TAG = "EmailWriterFragment";

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_email_title)
    TextView txtEmailTitle;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_record)
    Button btnRecord;
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
        ButterKnife.bind(this,v);
        setParentView(v);
        Typeface typeface = AppUtils.getAppTypeface(getContext());
        txtTitle.setTypeface(typeface);
        txtEmailTitle.setTypeface(typeface);
        etEmail.setTypeface(typeface);
        btnSave.setTypeface(typeface);
        btnRecord.setTypeface(typeface);
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
    public void setPresenter(EmailWriterContract.Presenter presenter) {
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
        presenter.writeTag(intent, etEmail.getText().toString());
    }

}
