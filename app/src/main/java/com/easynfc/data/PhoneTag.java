package com.easynfc.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pablorojas on 13/3/18.
 */

@Entity(tableName = "PhoneTag")
public class PhoneTag {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "timestamp")
    private long timeStamp;

    @ColumnInfo(name = "phone")
    private String phone;

    public PhoneTag(long timeStamp, String phone) {
        this.timeStamp = timeStamp;
        this.phone = phone;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}