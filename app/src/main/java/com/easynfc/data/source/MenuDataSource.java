package com.easynfc.data.source;

import com.easynfc.data.Menu;

import java.util.List;

/**
 * Created by pablorojas on 20/2/18.
 */

public interface MenuDataSource {

    void getMenu(LoadMenuCallback callback);

    interface LoadMenuCallback {
        void onMenusLoaded(List<Menu> menus);
        void onDataNotAvailable();
    }
}
