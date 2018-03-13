package com.easynfc.data.source.local.tags;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.easynfc.data.TextTag;
import com.easynfc.data.UrlTag;
import com.easynfc.data.MyTag;

import java.util.List;

/**
 * Created by pablorojas on 4/2/18.
 */

@Dao
public interface TagsDao {

    @Query("SELECT * FROM MyTag")
    List<MyTag> getTags();

    @Insert
    void insertTextTags(TextTag... textTags);

    @Query("DELETE FROM TextTag")
    void deleteAllTextTags();

    @Insert
    void insertUrlTag(UrlTag urlTag);

    @Insert
    void addTag(MyTag myTag);
}
