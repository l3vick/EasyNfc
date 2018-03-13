package com.easynfc.writer.wi_fi;


import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.easynfc.R;
import com.easynfc.data.model.Wifi;
import com.easynfc.util.AppUtils;
import com.easynfc.writer.BaseTypeFragment;

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
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_record)
    Button btnRecord;
    @BindView(R.id.parentView)
    FrameLayout parentView;
    ProgressBar progressBar;
    private RelativeLayout wifiListView;
    private ListView list;


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
        Typeface typeface = AppUtils.getAppTypeface(getContext());
        txtTitle.setTypeface(typeface);
        txtWifiSsidTitle.setTypeface(typeface);
        txtWifiPasswordTitle.setTypeface(typeface);
        etWifiSsid.setTypeface(typeface);
        etWifiPassword.setTypeface(typeface);
        btnSave.setTypeface(typeface);
        btnRecord.setTypeface(typeface);
        final SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_item, presenter.getSecurityCypherList(), "Select Security cypher");
        spSecurityCypher.setAdapter(adapter);


        //WIFI NETWORKS DIALOG RELATED
        wifiListView = (RelativeLayout) inflater.inflate(R.layout.wifi_dialog_list, null);
        list = wifiListView.findViewById(R.id.wifilist);
        TextView txtTitle = wifiListView.findViewById(R.id.txtDialogTitle);
        Button btnCustom = wifiListView.findViewById(R.id.btn_custom_wifi_list);
        progressBar = wifiListView.findViewById(R.id.progress_wheel);
        txtTitle.setTypeface(typeface);
        btnCustom.setTypeface(typeface);
        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideWifiNetowrksDialog();
            }
        });
        showWifiNetworksDialog();
        return v;
    }


    private void showWifiNetworksDialog() {
        parentView.addView(wifiListView);
    }

    private void hideWifiNetowrksDialog() {
        ((ViewGroup) wifiListView.getParent()).removeView(wifiListView);
    }

    @OnClick(R.id.btn_record)
    void onRecordTagBtnPressed() {
        presenter.enableForegroundDispatch();
        super.showDialog();
    }

    @OnClick(R.id.btn_save)
    void onSaveTagBtnPressed() {
        presenter.saveTag(etWifiSsid.getText().toString(), etWifiPassword.getText().toString(), spSecurityCypher.getSelectedItem().toString());
    }

    @OnClick(R.id.btn_wifi_networks)
    void onShowWifiNetworksDialogBtn() {
        showWifiNetworksDialog();
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
        presenter.writeTag(intent, etWifiSsid.getText().toString(), etWifiPassword.getText().toString(), spSecurityCypher.getSelectedItem().toString());
    }


    @Override
    public void onResume() {
        super.onResume();

        getActivity().registerReceiver(presenter.getWifiScanReceiver(),
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        presenter.startScan(new WiFiWriterContract.OnWifiNetworksLoadedCallback() {
            @Override
            public void OnSuccess(ArrayList<Wifi> wifis) {
                progressBar.setVisibility(View.GONE);
                showWifiNetworksList(wifis);
            }
        });
    }

    private void showWifiNetworksList(final ArrayList<Wifi> wifis) {
        List<String> adapterList = new ArrayList<>();
        for (Wifi wifi : wifis) {
            adapterList.add(wifi.getSsid());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.aar_item_tv, adapterList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int row, long l) {
                etWifiSsid.setText(wifis.get(row).getSsid());
                int position = presenter.getWifiAuthPosition(wifis.get(row).getSecurity().toString());
                spSecurityCypher.setSelection(position + 1);
                hideWifiNetowrksDialog();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter.getWifiScanReceiver() != null) {
            try {
                getActivity().unregisterReceiver(presenter.getWifiScanReceiver());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

}
