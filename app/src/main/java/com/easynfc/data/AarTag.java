package com.easynfc.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pablorojas on 13/3/18.
 */
@Entity(tableName = "AarTag")
public class AarTag {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "timestamp")
    private long timeStamp;

    @ColumnInfo(name = "aar")
    private String aar;

    public AarTag(long timeStamp, String aar) {
        this.timeStamp = timeStamp;
        this.aar = aar;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAar() {
        return aar;
    }

    public void setAar(String aar) {
        this.aar = aar;
    }

}
