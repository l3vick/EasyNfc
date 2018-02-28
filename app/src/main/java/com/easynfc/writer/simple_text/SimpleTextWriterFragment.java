package com.easynfc.writer.simple_text;


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
import com.easynfc.writer.BaseTypeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleTextWriterFragment extends BaseTypeFragment implements SimpleTextWriterContract.View {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_simple_text_title)
    TextView txtInputTitle;
    @BindView(R.id.et_simple_text)
    EditText etSimpleText;
    private SimpleTextWriterContract.Presenter presenter;

    public static final String TAG = "SimpleTextWriterFragment";

    public SimpleTextWriterFragment() {
        // Required empty public constructor
    }

    public static SimpleTextWriterFragment newInstance() {
        return new SimpleTextWriterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_writer_simple_text, container, false);
        setParentView(v);
        ButterKnife.bind(this, v);
        Typeface exo2 = Typeface.createFromAsset(getContext().getAssets(), "exo2.ttf");
        txtTitle.setTypeface(exo2);
        txtInputTitle.setTypeface(exo2);
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
    public void setPresenter(SimpleTextWriterContract.Presenter presenter) {
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
        presenter.writeTag(intent, etSimpleText.getText().toString());
    }
}
