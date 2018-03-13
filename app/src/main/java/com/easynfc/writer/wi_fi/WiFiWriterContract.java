package com.easynfc.writer.wi_fi;

import android.content.BroadcastReceiver;
import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.model.Wifi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablorojas on 28/2/18.
 */

public interface WiFiWriterContract {

    interface View extends BaseView<Presenter> {

        void OnTagWritten();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String ssid, String password, String securityCypher);

        void disableForegroundDispatch();

        List<String> getSecurityCypherList();

        BroadcastReceiver getWifiScanReceiver();

        void startScan(OnWifiNetworksLoadedCallback onWifiNetworksLoadedCallback);

        int getWifiAuthPosition(String authType);
    }

    interface OnWifiNetworksLoadedCallback {
        void OnSuccess(ArrayList<Wifi> wifi);
    }
}

