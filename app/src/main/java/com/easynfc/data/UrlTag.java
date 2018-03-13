package com.easynfc.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pablorojas on 13/3/18.
 */

@Entity(tableName = "UrlTag")
public class UrlTag {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "timestamp")
    private long timeStamp;

    @ColumnInfo(name = "content")
    private String content;

    public UrlTag(long timeStamp, String content) {
        this.timeStamp = timeStamp;
        this.content = content;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
