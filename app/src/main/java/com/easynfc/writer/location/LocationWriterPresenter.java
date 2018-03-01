package com.easynfc.writer.location;

import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.util.Log;

import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.util.LocationUtils;
import com.easynfc.util.NfcUtils;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;

/**
 * Created by pablorojas on 28/2/18.
 */

public class LocationWriterPresenter implements LocationWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private LocationWriterContract.View view;
    private static String TAG = "LocationWriterPresenter";
    //Location Api
    private LocationUtils locationManager;

    public LocationWriterPresenter(LocationWriterContract.View view, NfcUtils nfcUtils) {
        if (nfcUtils != null) {
            this.nfcUtils = nfcUtils;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "LocationWriterPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "LocationWriterPresenter: Users Repository can't be null.");
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
    public void writeTag(Intent intent, String latitude, String longitude) {
        try {
            nfcUtils.writeLocationTag(intent, latitude, longitude, new NfcUtils.TagWrittenCallback() {
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
    public void initLocationManager(Context context) {
        locationManager = LocationUtils.getInstance();
        locationManager.initLocationManager(context);
    }

    @Override
    public void connectApiClient() {
        locationManager.connect();
    }

    @Override
    public boolean isPermissionGranted() {
        return locationManager.isPermissionGranted();
    }

    @Override
    public void removeLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(locationManager.getGoogleApiClient(), locationManager);
    }

    @Override
    public void setPermissionGranted(boolean permissionGranted) {
        locationManager.setPermissionGranted(permissionGranted);
    }

    @Override
    public void disconnectApiClient() {
        locationManager.getGoogleApiClient().disconnect();
    }


    @Override
    public Double getLatitude() {
        return locationManager.getLatitude();
    }

    @Override
    public Double getLongitude() {
        return locationManager.getLongitude();
    }

    @Override
    public void requestLocationUpdates() {
        locationManager.requestLocationUpdates();
    }

}
