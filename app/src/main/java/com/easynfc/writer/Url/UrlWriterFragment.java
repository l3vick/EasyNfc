package com.easynfc.writer.url;


import android.content.Intent;
import android.graphics.Typeface;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UrlWriterFragment extends BaseTypeFragment implements UrlWriterContract.View {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_simple_text_title)
    TextView txtInputTitle;
    @BindView(R.id.et_url)
    EditText etUrl;
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
        txtTitle.setTypeface(AppUtils.getAppTypeface(getContext()));
        txtInputTitle.setTypeface(AppUtils.getAppTypeface(getContext()));
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
    public void setPresenter(UrlWriterContract.Presenter presenter) {
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
        presenter.writeTag(intent, etUrl.getText().toString());
    }
}
