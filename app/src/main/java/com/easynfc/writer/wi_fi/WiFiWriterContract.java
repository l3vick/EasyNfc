package com.easynfc.writer.wi_fi;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.model.WifiTag;

/**
 * Created by pablorojas on 28/2/18.
 */

public interface WiFiWriterContract {

    interface View extends BaseView<Presenter> {

        void OnTagWritten();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String ssid, String securityCypher, String password);

        void disableForegroundDispatch();
    }

    interface OnWifiItemClickedCallback {
        void OnSuccess(WifiTag wifiTag);
    }
}

