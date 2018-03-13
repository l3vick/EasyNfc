package com.easynfc.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pablorojas on 13/3/18.
 */

@Entity(tableName = "SmsTag")
public class SmsTag {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "timestamp")
    private long timeStamp;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "text")
    private String text;

    public SmsTag(long timeStamp, String number, String text) {
        this.timeStamp = timeStamp;
        this.number = number;
        this.text = text;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}