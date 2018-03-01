package com.easynfc.writer.location;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.easynfc.R;
import com.easynfc.util.AppConstants;
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
public class LocationWriterFragment extends BaseTypeFragment implements LocationWriterContract.View {


    public static final String TAG = "LocationWriterFragment";

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_latitude_title)
    TextView txtLatitudeTitle;
    @BindView(R.id.txt_longitude_title)
    TextView txtLongitudeTitle;
    @BindView(R.id.et_latitude)
    EditText etLatitude;
    @BindView(R.id.et_longitude)
    EditText etLongitude;
    public LocationWriterContract.Presenter presenter;

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
        txtTitle.setTypeface(typeface);
        txtLatitudeTitle.setTypeface(typeface);
        txtLongitudeTitle.setTypeface(typeface);
        etLatitude.setTypeface(typeface);
        etLongitude.setTypeface(typeface);
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

    @Override
    public void setPresenter(LocationWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void OnTagWritten() {
        super.tagWritten();
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
}
