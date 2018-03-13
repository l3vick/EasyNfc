package com.easynfc.mytags;

import android.util.Log;

import com.easynfc.data.Text;
import com.easynfc.data.source.TagsDataSource;
import com.easynfc.data.source.TagsRepository;

import java.util.List;

/**
 * Created by pablorojas on 13/3/18.
 */

public class MyTagsPresenter implements MyTagsContract.Presenter {


    private static final String TAG = "MyTagsPresenter";
    private MyTagsContract.View view;
    private TagsRepository tagsRepository;


    public MyTagsPresenter(MyTagsContract.View view, TagsRepository tagsRepository) {
        if (tagsRepository != null){
            this.tagsRepository = tagsRepository;
            if (view != null){
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "View can't be null.");
            }
        } else {
            Log.d(TAG, "Tags Repository can't be null.");
        }

    }

    @Override
    public void start() {
        tagsRepository.getTagMenu(new TagsDataSource.LoadTextTagsCallback() {
            @Override
            public void onTextTagsLoaded(List<Text> textTags) {
                view.setTags(textTags);
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
