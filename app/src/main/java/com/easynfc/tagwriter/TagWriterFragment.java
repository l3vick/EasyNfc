package com.easynfc.tagwriter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.BaseFragment;
import com.easynfc.MainActivity;
import com.easynfc.R;
import com.easynfc.tagreader.TagReaderFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagWriterFragment extends BaseFragment {


    public static final String TAG = "TagWriterFragment";

    public TagWriterFragment() {
        // Required empty public constructor
    }

    public static TagWriterFragment newInstance() {
        return new TagWriterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_writer, container, false);
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
