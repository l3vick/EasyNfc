package com.easynfc.data.source;

import com.easynfc.data.Text;
import com.easynfc.data.source.local.tags.TagsLocalDataSource;

import java.util.List;

/**
 * Created by pablorojas on 4/2/18.
 */

public class TagsRepository implements TagsDataSource {

    private static final String TAG = "TagsRepository";
    private static TagsRepository INSTANCE = null;
    private TagsDataSource remoteDataSource;

    public TagsRepository(TagsLocalDataSource remoteDataSource) {
        if (remoteDataSource != null) {
            this.remoteDataSource = remoteDataSource;
        }
    }

    public static TagsRepository getInstance(TagsLocalDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TagsRepository(remoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getTagMenu(final LoadTextTagsCallback callback) {
        remoteDataSource.getTagMenu(new LoadTextTagsCallback() {
            @Override
            public void onTextTagsLoaded(List<Text> textTags) {
                callback.onTextTagsLoaded(textTags);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void addAllTagMenu(Text... textTags) {
        remoteDataSource.addAllTagMenu(textTags);
    }

}
