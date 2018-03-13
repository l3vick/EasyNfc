package com.easynfc.mytags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.easynfc.BaseFragment;
import com.easynfc.R;
import com.easynfc.data.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTagsFragment extends BaseFragment implements MyTagsContract.View {


    public static final String TAG = "MyTagsFragment";

    private MyTagsContract.Presenter presenter;

    public MyTagsFragment() {
        // Required empty public constructor
    }

    public static MyTagsFragment newInstance() {
        return new MyTagsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_tags, container, false);
        presenter.start();
        return v;
    }


    @Override
    public void setPresenter(MyTagsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setTags(List<Text> textTags) {
        Toast.makeText(main, textTags.get(0).getContent()+ " " +textTags.get(0).getTimeStamp(), Toast.LENGTH_SHORT).show();
    }
}
