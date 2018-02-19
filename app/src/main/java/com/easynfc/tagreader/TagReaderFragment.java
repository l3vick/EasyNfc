package com.easynfc.tagreader;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.BaseFragment;
import com.easynfc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagReaderFragment extends BaseFragment {


    public static final String TAG = "TagReaderFragment";

    public TagReaderFragment() {
        // Required empty public constructor
    }

    public static TagReaderFragment newInstance(){
        return new TagReaderFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_reader, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                main.navigateToMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
