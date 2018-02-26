package com.easynfc.writer.SimpleText;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.TagMenu;
import com.easynfc.tagsmenu.TagsMenuContract;

import java.util.List;

/**
 * Created by pablorojas on 26/2/18.
 */

public interface SimpleTextWriterContract {

    interface View extends BaseView<Presenter> {
        void OnTagWritten();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String text);

        void disableForegroundDispatch();
    }
}
