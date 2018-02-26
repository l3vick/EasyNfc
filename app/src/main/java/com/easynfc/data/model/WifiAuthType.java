package com.easynfc.data.model;

/**
 * Created by pablorojas on 20/11/17.
 */

public enum WifiAuthType {
    OPEN("Open"),
    WEP("WEP"),
    WPA_PSK("WPA PSK"),
    WPA_EAP("WPA EAP"),
    WPA2_EAP("WPA2 EAP"),
    WPA2_PSK("WPA2 PSK"),
    NOT_DEFINED("NOT FOUND");

    private final String printableName;

    WifiAuthType(String printableName) {
        this.printableName = printableName;
    }

    @Override
    public String toString() {
        return printableName;
    }
}
