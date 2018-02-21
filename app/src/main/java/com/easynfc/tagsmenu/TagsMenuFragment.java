package com.easynfc.tagsmenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.R;
import com.easynfc.menu.MenuAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagsMenuFragment extends Fragment {


    public static final String TAG = "TagsMenuFragment";

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private MenuAdapter adapter;

    public TagsMenuFragment() {
        // Required empty public constructor
    }

    public static TagsMenuFragment newInstance(){
        return new TagsMenuFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tags_menu, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

}
