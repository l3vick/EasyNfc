package com.easynfc.writer.wi_fi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.easynfc.R;
import com.easynfc.writer.BaseTypeFragment;
import com.easynfc.writer.sms.SmsWriterContract;
import com.easynfc.writer.sms.SmsWriterFragment;
import com.easynfc.writer.url.UrlWriterContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class WiFiWriterFragment extends BaseTypeFragment implements WiFiWriterContract.View {


    public static final String TAG = "WiFiWriterFragment";

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_wifi_password_title)
    TextView txtWifiPasswordTitle;
    @BindView(R.id.et_wifi_password)
    EditText etWifiPassword;
    @BindView(R.id.txt_wifi_ssid_title)
    TextView txtWifiSsidTitle;
    @BindView(R.id.et_wifi_ssid)
    EditText etWifiSsid;
    @BindView(R.id.sp_security_cypher)
    Spinner spSecurityCypher;
    public WiFiWriterContract.Presenter presenter;

    public WiFiWriterFragment() {
        // Required empty public constructor
    }

    public static WiFiWriterFragment newInstance() {
        return new WiFiWriterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_writer_wi_fi, container, false);
        ButterKnife.bind(this, v);
        setParentView(v);
        List<String> securityCypherList = new ArrayList<>();
        securityCypherList.add("Open");
        securityCypherList.add("WEP");
        securityCypherList.add("WPA PSK");
        securityCypherList.add("WPA EAP");
        securityCypherList.add("WPA2 EAP");
        securityCypherList.add("WPA2 PSK");
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_item, securityCypherList, "Select Security cypher");
        spSecurityCypher.setAdapter(adapter);
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
    public void setPresenter(WiFiWriterContract.Presenter presenter) {
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
        //presenter.writeTag(intent, etUrl.getText().toString());
    }

}
