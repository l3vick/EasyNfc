package com.easynfc.menu;

import android.util.Log;

import com.easynfc.data.Menu;
import com.easynfc.data.source.MenuDataSource;
import com.easynfc.data.source.MenuRepository;

import java.util.List;

/**
 * Created by pablorojas on 20/2/18.
 */

public class MenuPresenter implements MenuContract.Presenter{

    private static final String TAG = "MenuPresenter";
    private MenuRepository menuRepository;
    private MenuContract.View view;


    public MenuPresenter(MenuRepository menuRepository, MenuContract.View view) {
        if (menuRepository != null) {
            this.menuRepository = menuRepository;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "CreateEditPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "CreateEditPresenter: Users Repository can't be null.");
        }

    }


    @Override
    public void start() {
        menuRepository.getMenu(new MenuDataSource.LoadMenuCallback() {
            @Override
            public void onMenusLoaded(List<Menu> menus) {
                view.setMenu(menus);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void stop() {
        view = null;
    }
}
