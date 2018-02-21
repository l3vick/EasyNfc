package com.easynfc.data.source.local.menu;

import android.content.Context;

import com.easynfc.data.Menu;
import com.easynfc.data.source.MenuDataSource;
import com.easynfc.data.source.local.EasyNfcDatabase;

import java.util.List;

/**
 * Created by pablorojas on 20/2/18.
 */

public class MenuLocalDataSource implements MenuDataSource {

    private static final String TAG = "MenuLocalDataSource";

    private static MenuLocalDataSource INSTANCE;
    private static MenuDao menuDao;

    private MenuLocalDataSource() {

    }

    public static MenuLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MenuLocalDataSource();
            menuDao = EasyNfcDatabase.getAppDatabase(context).menuDao();
        }
        return INSTANCE;
    }


    @Override
    public void getMenu(final LoadMenuCallback callback) {
        final List<Menu> menus = menuDao.getAll();
        if (menus.size() > 0) {
            callback.onMenusLoaded(menus);
        } else {
            callback.onMenusLoaded(addMenuData());
        }
    }

    private void addMenu(Menu... menu) {
        menuDao.insertAll(menu);
    }

    private List<Menu> addMenuData() {
        Menu writeMenu = new Menu("Write/", "write & save your favorites tags");
        Menu readMenu = new Menu("Read/", "read tag content");
        Menu myTagsMenu = new Menu("My Tags/", "write, emulate & update your tags");
        addMenu(writeMenu, readMenu, myTagsMenu);
        return menuDao.getAll();
    }
}
