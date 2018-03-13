package com.easynfc.mytags;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.Text;
import com.easynfc.menu.MenuContract;

import java.util.List;

/**
 * Created by pablorojas on 13/3/18.
 */

public interface MyTagsContract {

    interface View extends BaseView<Presenter> {
        void setTags(List<Text> textTags);
    }

    interface Presenter extends BasePresenter {

    }
}
