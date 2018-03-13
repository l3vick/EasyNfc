package com.easynfc.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pablorojas on 13/3/18.
 */

@Entity(tableName = "MyTag")
public class MyTag {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "timestamp")
    long timestamp;

    @ColumnInfo(name = "content")
    String content;

    @ColumnInfo(name = "type")
    String type;


    public MyTag(long timestamp, String content, String type) {
        this.timestamp = timestamp;
        this.content = content;
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }
}
