package com.easynfc.mytags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.easynfc.BaseFragment;
import com.easynfc.R;
import com.easynfc.data.MyTag;
import com.easynfc.data.model.Menu;
import com.easynfc.menu.MenuAdapter;
import com.easynfc.util.AppConstants;
import com.easynfc.util.AppUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTagsFragment extends BaseFragment implements MyTagsContract.View {



    @BindView(R.id.recycler)
    RecyclerView recycler;
    private MyTagsAdapter adapter;
    private List<MyTag> lstMyTags;
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
        ButterKnife.bind(this,v);
        lstMyTags = new ArrayList<>();
        adapter = new MyTagsAdapter(getActivity(),lstMyTags, onMyTagClickListener);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recycler.setLayoutManager(mLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);
        presenter.start();
        return v;
    }


    @Override
    public void setPresenter(MyTagsContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void setTags(List<MyTag> tags) {
        Collections.reverse(tags);
        lstMyTags = tags;
        adapter.updateAdapter(tags);
    }

    @Override
    public void updateView(MyTag myTag) {
        int position = lstMyTags.indexOf(myTag);
        lstMyTags.remove(myTag);
        adapter.updateRemove(lstMyTags, position);
    }


    private MyTagsContract.OnMyTagClickListener onMyTagClickListener = new MyTagsContract.OnMyTagClickListener() {
        @Override
        public void OnDelete(MyTag myTag) {
            presenter.deleteTag(myTag);
        }

        @Override
        public void OnItemSelected(MyTag myTag) {
            main.navigateToWriter(AppConstants.getTypeTag(myTag.getType()), myTag.getTimestamp());
        }
    };
}
