package com.easynfc.reader;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.Menu;
import com.easynfc.data.model.TagData;
import com.easynfc.menu.MenuContract;

import java.util.List;

/**
 * Created by pablorojas on 7/3/18.
 */

public interface ReaderContract {

    interface View extends BaseView<Presenter> {

        void processNfc(Intent intent);

        void setTagData(TagData tagData);
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void disableForegroundDispatch();

        void processNfc(Intent intent);
    }
}
