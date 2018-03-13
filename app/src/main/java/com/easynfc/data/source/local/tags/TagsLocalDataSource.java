package com.easynfc.data.source.local.tags;

import android.content.Context;

import com.easynfc.data.TextTag;
import com.easynfc.data.UrlTag;
import com.easynfc.data.MyTag;
import com.easynfc.data.source.TagsDataSource;
import com.easynfc.data.source.local.EasyNfcDatabase;
import com.easynfc.util.AppConstants;

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
    public void getTags(LoadTagsCallback callback) {
        final List<MyTag> tags = tagsDao.getTags();
        if (tags.size() > 0) {
            callback.onTagsLoaded(tags);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void addText(TextTag textTag) {
        tagsDao.addTag(new MyTag(textTag.getTimeStamp(), textTag.getContent(), AppConstants.TEXT));
        tagsDao.insertTextTags(textTag);
    }

    @Override
    public void addUrl(UrlTag urlTag) {
        tagsDao.addTag(new MyTag(urlTag.getTimeStamp(), urlTag.getContent(), AppConstants.URL));
        tagsDao.insertUrlTag(urlTag);
    }
}
