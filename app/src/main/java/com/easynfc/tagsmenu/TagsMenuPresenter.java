package com.easynfc.tagsmenu;

import android.util.Log;

import com.easynfc.data.TagMenu;
import com.easynfc.data.source.MenuDataSource;
import com.easynfc.data.source.MenuRepository;
import com.easynfc.menu.MenuContract;

import java.util.List;

/**
 * Created by pablorojas on 21/2/18.
 */

public class TagsMenuPresenter implements TagsMenuContract.Presenter{

    private static final String TAG = "TagsMenuPresenter";
    private MenuRepository menuRepository;
    private TagsMenuContract.View view;


    public TagsMenuPresenter(MenuRepository menuRepository, TagsMenuContract.View view) {
        if (menuRepository != null) {
            this.menuRepository = menuRepository;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "TagsMenuPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "TagsMenuPresenter: Users Repository can't be null.");
        }

    }



    @Override
    public void start() {
        menuRepository.getTagMenu(new MenuDataSource.LoadTagsMenuCallback() {
            @Override
            public void onTagsMenuLoaded(List<TagMenu> tagsMenu) {
                view.setTagsMenu(tagsMenu);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void stop() {
        this.view = null;
    }
}
