package com.easynfc.tagsmenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.BaseFragment;
import com.easynfc.R;
import com.easynfc.data.Menu;
import com.easynfc.data.TagMenu;
import com.easynfc.menu.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagsMenuFragment extends BaseFragment implements TagsMenuContract.View {


    public static final String TAG = "TagsMenuFragment";

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private TagsMenuAdapter adapter;
    private TagsMenuContract.Presenter presenter;

    public TagsMenuFragment() {
        // Required empty public constructor
    }

    public static TagsMenuFragment newInstance() {
        return new TagsMenuFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tags_menu, container, false);
        ButterKnife.bind(this, v);
        List<TagMenu> tagsMenu = new ArrayList<>();
        adapter = new TagsMenuAdapter(getActivity(),tagsMenu);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);  //displays number of cards per row
        recycler.setLayoutManager(mLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //presenter.stop();
    }

    @Override
    public void setPresenter(TagsMenuContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setTagsMenu(List<TagMenu> tagsMenu) {
        adapter.update(tagsMenu);
    }
}
