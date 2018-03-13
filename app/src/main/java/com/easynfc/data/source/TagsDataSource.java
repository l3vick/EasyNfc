package com.easynfc.data.source;

import com.easynfc.data.Text;

import java.util.List;

/**
 * Created by pablorojas on 4/2/18.
 */

public interface TagsDataSource {

    void getTagMenu(LoadTextTagsCallback callback);

    void addAllTagMenu(Text... textTags);

    interface LoadTextTagsCallback {
        void onTextTagsLoaded(List<Text> textTags);
        void onDataNotAvailable();
    }


}
