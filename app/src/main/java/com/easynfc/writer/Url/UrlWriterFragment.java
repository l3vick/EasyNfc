package com.easynfc.writer.url;


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
import com.easynfc.data.UrlTag;
import com.easynfc.util.AppUtils;
import com.easynfc.util.shared.EditTextWatcher;
import com.easynfc.writer.BaseTypeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UrlWriterFragment extends BaseTypeFragment implements UrlWriterContract.View {

    @BindView(R.id.et_url)
    EditText etUrl;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_record)
    Button btnRecord;
    @BindView(R.id.btn_emulate)
    FloatingActionButton btnEmulate;
    private UrlWriterContract.Presenter presenter;

    public static final String TAG = "UrlWriterFragment";

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
        View v = inflater.inflate(R.layout.fragment_writer_url, container, false);
        setParentView(v);
        ButterKnife.bind(this, v);
        Typeface typeface = AppUtils.getAppTypeface(getContext());
        etUrl.setTypeface(typeface);
        etUrl.addTextChangedListener(new FieldTextWatcher());
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
        presenter.saveTag(etUrl.getText().toString());
    }

    @OnClick(R.id.btn_emulate)
    void onEmulateAarBtnPressed() {
        presenter.emulateTag(etUrl.getText().toString());
        super.showEmulateDialog();
    }


    @Override
    public void setPresenter(UrlWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void OnTagWritten() {
        super.tagWritten();
    }

    @Override
    public void showMessageSuccess() {
        super.showMessageSuccess();
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
        if (tagId != 0){
            presenter.loadTag(tagId, new UrlWriterContract.LoadUrlTagCallback() {
                @Override
                public void onTagLoaded(UrlTag urlTag) {
                    etUrl.setText(urlTag.getContent());
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
        presenter.writeTag(intent, etUrl.getText().toString());
    }

    @Override
    protected void onAnyTextChanged(int count) {
        tryEnableButtons();
    }

    private void tryEnableButtons() {
        btnSave.setEnabled(etUrl.getText().length() > 0);
        btnRecord.setEnabled(etUrl.getText().length() > 0);
    }


    private class FieldTextWatcher extends EditTextWatcher {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            super.onTextChanged(s, start, before, count);
            onAnyTextChanged(count);
        }
    }


}
