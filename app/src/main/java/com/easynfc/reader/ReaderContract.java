package com.easynfc.reader;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.model.TagResponse;

/**
 * Created by pablorojas on 7/3/18.
 */

public interface ReaderContract {

    interface View extends BaseView<Presenter> {

        void processNfc(Intent intent);

        void setTagData(TagResponse tagResponse);
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void disableForegroundDispatch();

        void processNfc(Intent intent);
    }
}
