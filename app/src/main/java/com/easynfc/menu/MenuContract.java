package com.easynfc.menu;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.Menu;

import java.util.List;

/**
 * Created by pablorojas on 20/2/18.
 */

public interface MenuContract {

    interface View extends BaseView<Presenter> {

        void setMenu(List<Menu> menus);
    }

    interface Presenter extends BasePresenter {

    }

}
