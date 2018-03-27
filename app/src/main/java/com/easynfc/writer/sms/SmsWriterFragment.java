package com.easynfc.writer.sms;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.easynfc.R;
import com.easynfc.data.SmsTag;
import com.easynfc.util.AppUtils;
import com.easynfc.util.shared.EditTextWatcher;
import com.easynfc.writer.BaseTypeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsWriterFragment extends BaseTypeFragment implements SmsWriterContract.View {


    @BindView(R.id.et_sms_phone)
    EditText etPhone;
    @BindView(R.id.et_sms_text)
    EditText etText;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_record)
    Button btnRecord;
    @BindView(R.id.btn_emulate)
    FloatingActionButton btnEmulate;
    public SmsWriterContract.Presenter presenter;

    public static final String TAG = "SmsWriterFragment";

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
        etPhone.setTypeface(typeface);
        etText.setTypeface(typeface);
        etPhone.addTextChangedListener(new FieldTextWatcher());
        etText.addTextChangedListener(new FieldTextWatcher());
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
        if (tagId != 0 ){
            presenter.updateTag(tagId, etPhone.getText().toString(), etText.getText().toString());
        }else{
            presenter.saveTag(etPhone.getText().toString(), etText.getText().toString());
        }

    }

    @OnClick(R.id.btn_emulate)
    void onEmulateAarBtnPressed() {
        presenter.emulateTag(etPhone.getText().toString(), etText.getText().toString());
        super.showEmulateDialog();
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
    public void showInserted() {
        super.showInserted();
        btnSave.setEnabled(false);
    }

    @Override
    public void showMessageError() {
        super.showMessageError();
    }

    @Override
    public void showUpdatedSuccess() {
        super.showUpdated();
        btnSave.setEnabled(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.disableForegroundDispatch();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tagId != 0){
            presenter.loadTag(tagId, new SmsWriterContract.LoadSmsTagCallback() {
                @Override
                public void onTagLoaded(SmsTag smsTag) {
                    etPhone.setText(smsTag.getNumber());
                    etText.setText(smsTag.getText());
                    btnRecord.setEnabled(true);
                    btnSave.setEnabled(false);
                    btnEmulate.setVisibility(View.VISIBLE);
                }

                @Override
                public void onDatanotAvailable() {

                }
            });
            btnSave.setText(R.string.update);
        }
    }

    @Override
    public void processNfc(Intent intent) {
        super.processNfc(intent);
        presenter.writeTag(intent, etPhone.getText().toString(), etText.getText().toString());
    }

    @Override
    protected void onAnyTextChanged(int count) {
        tryEnableButtons();
    }

    private void tryEnableButtons() {
        btnSave.setEnabled(etText.getText().length() > 0
                && etPhone.getText().length() > 0);
        btnRecord.setEnabled(etText.getText().length() > 0
                && etPhone.getText().length() > 0);
    }


    private class FieldTextWatcher extends EditTextWatcher {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            super.onTextChanged(s, start, before, count);
            onAnyTextChanged(count);
        }
    }


}
