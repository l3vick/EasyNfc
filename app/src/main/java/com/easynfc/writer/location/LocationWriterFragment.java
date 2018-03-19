package com.easynfc.writer.location;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.easynfc.R;
import com.easynfc.data.EmailTag;
import com.easynfc.data.LocationTag;
import com.easynfc.util.AppConstants;
import com.easynfc.util.AppUtils;
import com.easynfc.util.shared.EditTextWatcher;
import com.easynfc.writer.BaseTypeFragment;
import com.easynfc.writer.email.EmailWriterContract;
import com.easynfc.writer.sms.SmsWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationWriterFragment extends BaseTypeFragment implements LocationWriterContract.View {


    @BindView(R.id.et_latitude)
    EditText etLatitude;
    @BindView(R.id.et_longitude)
    EditText etLongitude;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_record)
    Button btnRecord;
    @BindView(R.id.btn_emulate)
    FloatingActionButton btnEmulate;
    public LocationWriterContract.Presenter presenter;
    
    public static final String TAG = "LocationWriterFragment";


    public LocationWriterFragment() {
        // Required empty public constructor
    }

    public static LocationWriterFragment newInstance() {
        return new LocationWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_writer_location, container, false);
        ButterKnife.bind(this,v);
        setParentView(v);
        Typeface typeface = AppUtils.getAppTypeface(getContext());
        etLatitude.setTypeface(typeface);
        etLatitude.addTextChangedListener(new FieldTextWatcher());
        etLongitude.setTypeface(typeface);
        etLongitude.addTextChangedListener(new FieldTextWatcher());
        btnSave.setTypeface(typeface);
        btnRecord.setTypeface(typeface);
        presenter.initLocationManager(getContext());
        return v;
    }




    @OnClick(R.id.btn_record)
    void onRecordTagBtnPressed() {
        presenter.enableForegroundDispatch();
        super.showDialog();
    }

    @OnClick(R.id.btn_save)
    void onSaveTagBtnPressed() {
        presenter.saveTag(etLatitude.getText().toString(), etLongitude.getText().toString());
    }

    @OnClick(R.id.btn_location)
    void onLocationBtnPressed(){
        if (presenter.isPermissionGranted()) {
            etLatitude.setText(String.valueOf(presenter.getLatitude()));
            etLongitude.setText(String.valueOf(presenter.getLongitude()));
        } else {
            presenter.requestLocationUpdates();
        }
    }

    @OnClick(R.id.btn_emulate)
    void onEmulateAarBtnPressed() {
        presenter.emulateTag(etLatitude.getText().toString(), etLongitude.getText().toString());
        super.showEmulateDialog();
    }

    @Override
    public void setPresenter(LocationWriterContract.Presenter presenter) {
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
    public void onStart() {
        super.onStart();
        presenter.connectApiClient();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.disconnectApiClient();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tagId != 0){
            presenter.loadTag(tagId, new LocationWriterContract.LoadLocationTagCallback() {

                @Override
                public void onTagLoaded(LocationTag locationTag) {
                    etLatitude.setText(locationTag.getLatitude());
                    etLongitude.setText(locationTag.getLongitude());
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstants.MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission granted
                presenter.setPermissionGranted(true);
            } else {
                //Permission denied
                presenter.setPermissionGranted(false);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.disableForegroundDispatch();
        if (presenter.isPermissionGranted()) {
            presenter.removeLocationUpdates();
        }
    }

    @Override
    public void processNfc(Intent intent) {
        super.processNfc(intent);
        presenter.writeTag(intent, etLatitude.getText().toString(), etLongitude.getText().toString());
    }

    @Override
    protected void onAnyTextChanged(int count) {
        tryEnableButtons();
    }

    private void tryEnableButtons() {
        btnSave.setEnabled(etLongitude.getText().length() > 0 && etLatitude.getText().length() > 0);
        btnRecord.setEnabled(etLongitude.getText().length() > 0 && etLatitude.getText().length() > 0);
    }


    private class FieldTextWatcher extends EditTextWatcher {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            super.onTextChanged(s, start, before, count);
            onAnyTextChanged(count);
        }
    }


}
