package com.easynfc.data.source;

import com.easynfc.data.Menu;
import com.easynfc.data.TagMenu;

import java.util.List;

/**
 * Created by pablorojas on 20/2/18.
 */

public class MenuRepository implements MenuDataSource {


    private static final String TAG = "MenuRepository";
    private static MenuRepository INSTANCE = null;
    private MenuDataSource remoteDataSource;

    // Prevent direct instantiation.
    private MenuRepository(MenuDataSource remoteDataSource) {
        if (remoteDataSource != null) {
            this.remoteDataSource = remoteDataSource;
        }
    }

    public static MenuRepository getInstance(MenuDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MenuRepository(remoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getMenu(final LoadMenuCallback callback) {
        remoteDataSource.getMenu(new LoadMenuCallback() {
            @Override
            public void onMenusLoaded(List<Menu> menus) {
                callback.onMenusLoaded(menus);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getTagMenu(final LoadTagsMenuCallback callback) {
        remoteDataSource.getTagMenu(new LoadTagsMenuCallback() {
            @Override
            public void onTagsMenuLoaded(List<TagMenu> tagsMenu) {
                callback.onTagsMenuLoaded(tagsMenu);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }
}
