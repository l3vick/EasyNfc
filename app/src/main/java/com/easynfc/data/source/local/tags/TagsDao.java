package com.easynfc.data.source.local.tags;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.easynfc.data.Text;

import java.util.List;

/**
 * Created by pablorojas on 4/2/18.
 */

@Dao
public interface TagsDao {

    @Query("SELECT * FROM Text")
    List<Text> getTextTags();

    @Insert
    void insertTextTags(Text... texts);

    @Query("DELETE FROM Text")
    void deleteAllTextTags();
}
