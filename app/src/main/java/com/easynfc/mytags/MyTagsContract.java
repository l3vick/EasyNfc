package com.easynfc.mytags;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.MyTag;

import java.util.List;

/**
 * Created by pablorojas on 13/3/18.
 */

public interface MyTagsContract {

    interface View extends BaseView<Presenter> {
        void setTags(List<MyTag> tags);

        void updateView(MyTag myTag);
    }

    interface Presenter extends BasePresenter {
        void deleteTag(MyTag myTag);
    }

    interface OnDeleteTagCallback{
        void OnSuccess();
    }
    interface OnMyTagClickListener{
        void OnDelete(MyTag myTag);
    }
}
