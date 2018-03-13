package com.easynfc.writer.wi_fi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.nfc.FormatException;
import android.util.Log;

import com.easynfc.data.WifiTag;
import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.data.model.WifiAuthType;
import com.easynfc.data.model.Wifi;
import com.easynfc.data.source.TagsRepository;
import com.easynfc.util.NfcUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablorojas on 28/2/18.
 */

public class WiFiWriterPresenter implements WiFiWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private WiFiWriterContract.View view;
    private TagsRepository tagsRepository;
    private ArrayList<Wifi> wifiNetworksList;
    private WifiManager wifiManager;
    private WiFiWriterContract.OnWifiNetworksLoadedCallback callback;
    private static String TAG = "WiFiWriterPresenter";

    public WiFiWriterPresenter(WiFiWriterContract.View view, NfcUtils nfcUtils, WifiManager wifiManager, TagsRepository tagsRepository) {
        if (nfcUtils != null && tagsRepository != null) {
            this.nfcUtils = nfcUtils;
            if (view != null) {
                this.view = view;
                this.wifiManager = wifiManager;
                this.tagsRepository = tagsRepository;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "View can't be null.");
            }
        } else {
            Log.d(TAG, "NfcUtils & Tags Repository can't be null.");
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        this.view = null;
    }

    @Override
    public void enableForegroundDispatch() {
        nfcUtils.enableForegroundDispatch();
    }

    @Override
    public void writeTag(Intent intent, String ssid, String password, String cypher) {
        Wifi wifi = new Wifi(ssid, toWifiAuthType(cypher), password);
        try {
            nfcUtils.writeWifiTag(intent, wifi, new NfcUtils.TagWrittenCallback() {
                @Override
                public void OnSuccess() {
                    view.OnTagWritten();
                }

                @Override
                public void OnError() {

                }
            });
        } catch (ReadOnlyTagException e) {
            e.printStackTrace();
        } catch (NdefFormatException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (InsufficientSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disableForegroundDispatch() {
        nfcUtils.disableForegroundDispatch();
    }


    @Override
    public List<String> getSecurityCypherList() {
        List<String> securityCypherList = new ArrayList<>();
        securityCypherList.add("Open");
        securityCypherList.add("WEP");
        securityCypherList.add("WPA PSK");
        securityCypherList.add("WPA EAP");
        securityCypherList.add("WPA2 EAP");
        securityCypherList.add("WPA2 PSK");
        return securityCypherList;
    }

    @Override
    public BroadcastReceiver getWifiScanReceiver() {
        return wifiScanReceiver;
    }

    @Override
    public void startScan(WiFiWriterContract.OnWifiNetworksLoadedCallback callback) {
        this.callback = callback;
        wifiManager.startScan();
    }

    @Override
    public int getWifiAuthPosition(String authType) {
       return toWifiAuthType(authType).ordinal();
    }

    @Override
    public void saveTag(String ssid, String password, String auth) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tagsRepository.addWifi(new WifiTag(timestamp.getTime(), ssid,auth, password));
    }


    private final BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                List<ScanResult> mScanResults = wifiManager.getScanResults();
                if (mScanResults.size() > 0) {
                    wifiNetworksList = new ArrayList<>();

                    for (ScanResult mScanResult : mScanResults) {
                        wifiNetworksList.add(new Wifi(mScanResult.SSID, toWifiAuthType(mScanResult.capabilities), null));
                    }
                    callback.OnSuccess(wifiNetworksList);
                }
            }
        }
    };

    private WifiAuthType toWifiAuthType(String authType) {
        if (authType.startsWith("[Open")) {
            return WifiAuthType.OPEN;
        } else if (authType.startsWith("[WEP")|| authType.startsWith("WEP")) {
            return WifiAuthType.WEP;
        } else if (authType.startsWith("[WPA2-EAP") || authType.startsWith("WPA2 EAP")) {
            return WifiAuthType.WPA2_EAP;
        } else if (authType.startsWith("[WPA-EAP")|| authType.startsWith("WPA EAP")) {
            return WifiAuthType.WPA_EAP;
        } else if (authType.startsWith("[WPA-PSK")|| authType.startsWith("WPA PSK")) {
            return WifiAuthType.WPA_PSK;
        } else if (authType.startsWith("[WPA2-PSK")|| authType.startsWith("WPA2 PSK")) {
            return WifiAuthType.WPA2_PSK;
        } else {
            return WifiAuthType.WPA2_PSK;
        }
    }
}
