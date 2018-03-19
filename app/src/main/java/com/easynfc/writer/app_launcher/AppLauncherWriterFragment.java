package com.easynfc.writer.app_launcher;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easynfc.R;
import com.easynfc.data.AarTag;
import com.easynfc.data.TextTag;
import com.easynfc.util.AppUtils;
import com.easynfc.util.shared.EditTextWatcher;
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


    @BindView(R.id.et_app_launcher)
    EditText etAppLauncher;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_record)
    Button btnRecord;
    @BindView(R.id.parentView)
    FrameLayout parentView;
    private RelativeLayout aarListView;

    public static final String TAG = "AppLauncherWriterFragment";

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
        View v = inflater.inflate(R.layout.fragment_writer_app_launcher, container, false);
        ButterKnife.bind(this, v);
        setParentView(v);
        Typeface typeface = AppUtils.getAppTypeface(getContext());
        etAppLauncher.setTypeface(typeface);
        etAppLauncher.addTextChangedListener(new FieldTextWatcher());
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
        presenter.saveTag(etAppLauncher.getText().toString());
    }

    @OnClick(R.id.btn_reload)
    void onReloadAarViewBtnPressed() {
        showAarList(new AppLauncherWriterContract.OnAarItemClickedCallback() {
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
            presenter.loadTag(tagId, new AppLauncherWriterContract.LoadAarTagCallback() {
                @Override
                public void onTagLoaded(AarTag aarTag) {
                    etAppLauncher.setText(aarTag.getAar());
                    btnRecord.setEnabled(true);
                    btnSave.setEnabled(false);
                }

                @Override
                public void onDatanotAvailable() {

                }
            });
        }else{
            showAarList(new AppLauncherWriterContract.OnAarItemClickedCallback() {
                @Override
                public void OnSuccess(String aar) {
                    etAppLauncher.setText(aar);
                }
            });
        }
    }

    @Override
    public void processNfc(Intent intent) {
        super.processNfc(intent);
        presenter.writeTag(intent, etAppLauncher.getText().toString());
    }

    @Override
    protected void onAnyTextChanged(int count) {
        tryEnableButtons();
    }

    private void tryEnableButtons() {
        btnSave.setEnabled(etAppLauncher.getText().length() > 0);
        btnRecord.setEnabled(etAppLauncher.getText().length() > 0);
    }


    private class FieldTextWatcher extends EditTextWatcher {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            super.onTextChanged(s, start, before, count);
            onAnyTextChanged(count);
        }
    }


    public void showAarList(AppLauncherWriterContract.OnAarItemClickedCallback callback) {
        setAarView(callback);
        parentView.addView(aarListView);
    }

    public void hideAarList() {
        ((ViewGroup) aarListView.getParent()).removeView(aarListView);
    }

    private void setAarView(final AppLauncherWriterContract.OnAarItemClickedCallback callback) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        aarListView = (RelativeLayout) inflater.inflate(R.layout.aar_dialog_list, null);

        ListView list = aarListView.findViewById(R.id.aarlist);

        final Typeface typeface = AppUtils.getAppTypeface(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.aar_item_tv, presenter.getInstalledPackageNameList(getActivity())) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(typeface);
                return v;
            }
        };

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int row, long l) {
                Object obj = adapterView.getItemAtPosition(row);
                callback.OnSuccess(obj.toString());
                hideAarList();
            }
        });

        TextView txtTitle = aarListView.findViewById(R.id.txtDialogTitle);
        Button btnCustom = aarListView.findViewById(R.id.btn_custom_aar_list);
        txtTitle.setTypeface(typeface);
        btnCustom.setTypeface(typeface);
        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAarList();
            }
        });
    }

}
