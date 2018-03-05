package com.easynfc.writer.app_launcher;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
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
import com.easynfc.writer.wi_fi.WiFiWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterFragment;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppLauncherWriterFragment extends BaseTypeFragment implements AppLauncherWriterContract.View {


    public static final String TAG = "AppLauncherWriterFragment";

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_app_launcher_title)
    TextView txtAppLauncherTitle;
    @BindView(R.id.et_app_launcher)
    EditText etAppLauncher;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_record)
    Button btnRecord;

    public AppLauncherWriterContract.Presenter presenter;

    public AppLauncherWriterFragment() {
        // Required empty public constructor
    }

    public static AppLauncherWriterFragment newInstance() {
        return new AppLauncherWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_writer_app_launcher, container, false);
        ButterKnife.bind(this,v);
        setParentView(v);
        Typeface typeface = AppUtils.getAppTypeface(getContext());
        txtTitle.setTypeface(typeface);
        txtAppLauncherTitle.setTypeface(typeface);
        etAppLauncher.setTypeface(typeface);
        btnSave.setTypeface(typeface);
        btnRecord.setTypeface(typeface);
        super.showAarList(new AppLauncherWriterContract.OnAarItemClickedCallback() {
            @Override
            public void OnSuccess(String aar) {
                etAppLauncher.setText(aar);
            }
        });
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

    @OnClick(R.id.btn_reload)
    void onReloadAarViewBtnPressed() {
        super.showAarList(new AppLauncherWriterContract.OnAarItemClickedCallback() {
            @Override
            public void OnSuccess(String aar) {
                etAppLauncher.setText(aar);
            }
        });
    }

    @Override
    public void setPresenter(AppLauncherWriterContract.Presenter presenter) {
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
        presenter.writeTag(intent, etAppLauncher.getText().toString());
    }
}
