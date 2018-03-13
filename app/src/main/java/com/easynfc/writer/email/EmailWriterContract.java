package com.easynfc.writer.email;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;

/**
 * Created by pablorojas on 28/2/18.
 */

public interface EmailWriterContract {

    interface View extends BaseView<Presenter> {

        void OnTagWritten();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String text);

        void disableForegroundDispatch();

        void saveTag(String email);
    }
}

