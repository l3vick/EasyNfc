package com.easynfc.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by pablorojas on 14/11/17.
 */

public class LocationUtils implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi locationProviderApi;
    private Context context;
    private Double latitude, longitude;
    private boolean permissionIsGranted = true;


    private LocationUtils() {

    }

    public static LocationUtils getInstance() {
        return new LocationUtils();
    }

    public void initLocationManager(Context context){
        this.context = context;
        this.googleApiClient = new GoogleApiClient.Builder(context).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        this.locationRequest = new LocationRequest();
        this.locationRequest.setInterval(60 * 1000);
        this.locationRequest.setFastestInterval(15 * 1000);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.locationProviderApi = LocationServices.FusedLocationApi;
    }

    public void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            permissionIsGranted = false;
            ActivityCompat.requestPermissions((Activity) context,new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.MY_PERMISSIONS_REQUEST_LOCATION);
            return;

        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        permissionIsGranted = true;
    }

    public GoogleApiClient getGoogleApiClient() {
        return this.googleApiClient;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public boolean isPermissionGranted() {
        if (permissionIsGranted){
            return permissionIsGranted;
        }else {
            return permissionIsGranted;
        }
    }

    public void setPermissionGranted(boolean permissionIsGranted) {
        this.permissionIsGranted = permissionIsGranted;
    }


    public void connect() {
        this.googleApiClient.connect();
    }
}
