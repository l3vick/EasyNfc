package com.easynfc.writer.location;

import android.content.Context;
import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.EmailTag;
import com.easynfc.data.LocationTag;

/**
 * Created by pablorojas on 28/2/18.
 */

public interface LocationWriterContract {

    interface View extends BaseView<Presenter> {

        void OnTagWritten();

        void showAddedSuccess();

        void showMessageError();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String latitude, String longitude);

        void disableForegroundDispatch();

        boolean isPermissionGranted();

        void removeLocationUpdates();

        void setPermissionGranted(boolean permissionGranted);

        void connectApiClient();

        void initLocationManager(Context context);

        void disconnectApiClient();

        Double getLatitude();

        Double getLongitude();

        void requestLocationUpdates();

        void saveTag(String latitude, String longitude);

        void loadTag(long tagId, LoadLocationTagCallback loadLocationTagCallback);

        void emulateTag(String latitude, String longitude);
    }

    interface LoadLocationTagCallback {
        void onTagLoaded(LocationTag locationTag);
        void onDatanotAvailable();
    }
}

