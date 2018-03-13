package com.easynfc.data.source.local.tags;

import android.content.Context;

import com.easynfc.data.Text;
import com.easynfc.data.source.TagsDataSource;
import com.easynfc.data.source.local.EasyNfcDatabase;

import java.util.List;

/**
 * Created by pablorojas on 4/2/18.
 */

public class TagsLocalDataSource implements TagsDataSource {

    private static final String TAG = "MenuLocalDataSource";

    private static TagsLocalDataSource INSTANCE;
    private static TagsDao tagsDao;

    private TagsLocalDataSource() {

    }

    public static TagsLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TagsLocalDataSource();
            tagsDao = EasyNfcDatabase.getAppDatabase(context).tagsDao();
        }
        return INSTANCE;
    }

    @Override
    public void getTagMenu(LoadTextTagsCallback callback) {
        final List<Text> textTags = tagsDao.getTextTags();
        if (textTags.size() > 0) {
            callback.onTextTagsLoaded(textTags);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void addAllTagMenu(Text... textTags) {
        tagsDao.insertTextTags(textTags);
    }
}
