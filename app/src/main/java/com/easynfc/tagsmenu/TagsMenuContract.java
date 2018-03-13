package com.easynfc.tagsmenu;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.model.TagMenu;

import java.util.List;

/**
 * Created by pablorojas on 21/2/18.
 */

public interface TagsMenuContract {

    interface View extends BaseView<Presenter> {

        void setTagsMenu(List<TagMenu> tagsMenu);
    }

    interface Presenter extends BasePresenter{

    }
}
