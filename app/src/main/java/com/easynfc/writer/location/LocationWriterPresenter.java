package com.easynfc.writer.location;

import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.util.Log;

import com.easynfc.data.LocationTag;
import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.data.source.TagsDataSource;
import com.easynfc.data.source.TagsRepository;
import com.easynfc.util.LocationUtils;
import com.easynfc.util.NfcUtils;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by pablorojas on 28/2/18.
 */

public class LocationWriterPresenter implements LocationWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private LocationWriterContract.View view;
    private LocationUtils locationManager;
    private TagsRepository tagsRepository;
    private static String TAG = "LocationWriterPresenter";

    public LocationWriterPresenter(LocationWriterContract.View view, NfcUtils nfcUtils, TagsRepository tagsRepository) {
        if (nfcUtils != null && tagsRepository != null) {
            this.nfcUtils = nfcUtils;
            this.tagsRepository = tagsRepository;
            if (view != null) {
                this.view = view;
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

    @Override
    public void saveTag(String latitude, String longitude) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tagsRepository.addLocation(new LocationTag(timestamp.getTime(), latitude, longitude), new TagsDataSource.OnTagSavedCallback() {
            @Override
            public void onSuccess() {
                view.showMessageSuccess();
            }

            @Override
            public void onError() {
                view.showMessageError();
            }
        });
    }

    @Override
    public void loadTag(long tagId, final LocationWriterContract.LoadLocationTagCallback callback) {
        tagsRepository.getLocationTag(tagId, new LocationWriterContract.LoadLocationTagCallback() {
            @Override
            public void onTagLoaded(LocationTag locationTag) {
                callback.onTagLoaded(locationTag);
            }

            @Override
            public void onDatanotAvailable() {
                callback.onDatanotAvailable();
            }
        });
    }

}
