package com.easynfc.data.model;

/**
 * Created by pablorojas on 18/11/17.
 */

public class WifiTag {

    String ssid;
    String password;
    WifiAuthType security;



    public WifiTag() {
    }

    public WifiTag(String ssid, WifiAuthType security, String password) {
        this.ssid = ssid;
        this.security = security;
        this.password = password;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public WifiAuthType getSecurity() {
        return security;
    }

    public void setSecurity(WifiAuthType security) {
        this.security = security;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
