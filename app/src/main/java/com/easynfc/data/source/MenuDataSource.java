package com.easynfc.data.source;

import com.easynfc.data.Menu;
import com.easynfc.data.TagMenu;

import java.util.List;

/**
 * Created by pablorojas on 20/2/18.
 */

public interface MenuDataSource {

    void getMenu(LoadMenuCallback callback);
    void getTagMenu(LoadTagsMenuCallback loadTagsMenuCallback);

    interface LoadMenuCallback {
        void onMenusLoaded(List<Menu> menus);
        void onDataNotAvailable();
    }

    interface LoadTagsMenuCallback {
        void onTagsMenuLoaded(List<TagMenu> tagsMenu);
        void onDataNotAvailable();
    }
}
