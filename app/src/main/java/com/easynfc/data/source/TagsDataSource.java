package com.easynfc.data.source;

import com.easynfc.data.AarTag;
import com.easynfc.data.EmailTag;
import com.easynfc.data.LocationTag;
import com.easynfc.data.PhoneTag;
import com.easynfc.data.SmsTag;
import com.easynfc.data.TextTag;
import com.easynfc.data.UrlTag;
import com.easynfc.data.MyTag;
import com.easynfc.data.WifiTag;
import com.easynfc.mytags.MyTagsContract;

import java.util.List;

/**
 * Created by pablorojas on 4/2/18.
 */

public interface TagsDataSource {

    void getTags(LoadTagsCallback callback);

    void addText(TextTag textTag);

    void addUrl(UrlTag urlTag);

    void addSms(SmsTag smsTag);

    void addPhone(PhoneTag phoneTag);

    void addAar(AarTag aarTag);

    void addLocation(LocationTag locationTag);

    void addWifi(WifiTag wifiTag);

    void addEmail(EmailTag emailTag);

    void deleteTag(MyTag myTag, MyTagsContract.OnDeleteTagCallback callback);

    interface LoadTagsCallback {

        void onTagsLoaded(List<MyTag> tags);

        void onDataNotAvailable();
    }


}
