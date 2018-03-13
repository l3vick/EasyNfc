package com.easynfc.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pablorojas on 13/3/18.
 */

@Entity(tableName = "WifiTag")
public class WifiTag {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "timestamp")
    private long timeStamp;

    @ColumnInfo(name = "ssid")
    private String ssid;

    @ColumnInfo(name = "auth")
    private String auth;

    @ColumnInfo(name = "password")
    private String password;


    public WifiTag(long timeStamp, String ssid, String auth, String password) {
        this.timeStamp = timeStamp;
        this.ssid = ssid;
        this.auth = auth;
        this.password = password;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
