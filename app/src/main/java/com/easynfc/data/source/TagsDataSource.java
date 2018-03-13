package com.easynfc.data.source;

import com.easynfc.data.TextTag;
import com.easynfc.data.UrlTag;
import com.easynfc.data.MyTag;

import java.util.List;

/**
 * Created by pablorojas on 4/2/18.
 */

public interface TagsDataSource {

    void getTags(LoadTagsCallback callback);

    void addText(TextTag textTag);

    void addUrl(UrlTag urlTag);

    interface LoadTagsCallback {

        void onTagsLoaded(List<MyTag> tags);

        void onDataNotAvailable();
    }


}
