package com.easynfc.writer.email;


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
import com.easynfc.data.EmailTag;
import com.easynfc.util.AppUtils;
import com.easynfc.util.shared.EditTextWatcher;
import com.easynfc.writer.BaseTypeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailWriterFragment extends BaseTypeFragment implements EmailWriterContract.View {


    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_record)
    Button btnRecord;
    @BindView(R.id.btn_emulate)
    FloatingActionButton btnEmulate;
    public EmailWriterContract.Presenter presenter;

    public static final String TAG = "EmailWriterFragment";

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
        View v = inflater.inflate(R.layout.fragment_writer_email, container, false);
        ButterKnife.bind(this, v);
        setParentView(v);
        Typeface typeface = AppUtils.getAppTypeface(getContext());
        etEmail.setTypeface(typeface);
        etEmail.addTextChangedListener(new FieldTextWatcher());
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
        presenter.saveTag(etEmail.getText().toString());
    }

    @OnClick(R.id.btn_emulate)
    void onEmulateAarBtnPressed() {
        presenter.emulateTag(etEmail.getText().toString());
        super.showEmulateDialog();
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
    public void showInserted() {
        super.showInserted();
        btnSave.setEnabled(false);
    }

    @Override
    public void showMessageError() {
        super.showMessageError();
    }



    @Override
    public void onPause() {
        super.onPause();
        presenter.disableForegroundDispatch();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tagId != 0) {
            presenter.loadTag(tagId, new EmailWriterContract.LoadEmailTagCallback() {
                @Override
                public void onTagLoaded(EmailTag emailTag) {
                    etEmail.setText(emailTag.getContent());
                    btnRecord.setEnabled(true);
                    btnSave.setEnabled(false);
                    btnEmulate.setVisibility(View.VISIBLE);
                }

                @Override
                public void onDatanotAvailable() {

                }
            });
        }
    }

    @Override
    public void processNfc(Intent intent) {
        super.processNfc(intent);
        presenter.writeTag(intent, etEmail.getText().toString());
    }

    @Override
    protected void onAnyTextChanged(int count) {
        tryEnableButtons();
    }

    private void tryEnableButtons() {
        btnSave.setEnabled(etEmail.getText().length() > 0);
        btnRecord.setEnabled(etEmail.getText().length() > 0);
    }


    private class FieldTextWatcher extends EditTextWatcher {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            super.onTextChanged(s, start, before, count);
            onAnyTextChanged(count);
        }
    }

}
