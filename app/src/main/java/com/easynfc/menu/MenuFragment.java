package com.easynfc.menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easynfc.MainActivity;
import com.easynfc.R;
import com.easynfc.data.Menu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    private MenuAdapter adapter;
    private GridLayoutManager mLayoutManager;

    private MainActivity main;


    public static final String TAG = "MenuFragment";

    public MenuFragment() {

    }

    public static MenuFragment newInstance(){
        return  new MenuFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this,v);
        adapter = new MenuAdapter(getActivity(), getMenuList());
        mLayoutManager = new GridLayoutManager(getActivity(), 1);  //displays number of cards per row
        recycler.setLayoutManager(mLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);
        return v;
    }

    private List<Menu> getMenuList() {
        List<Menu> lst_menu = new ArrayList<>();
        Menu writeMenu = new Menu("Write/","write & save your favorites tags");
        Menu readMenu = new Menu("Read/","read tag content");
        Menu myTagsMenu = new Menu("My Tags/","write, emulate & update your tags");
        lst_menu.add(writeMenu);
        lst_menu.add(readMenu);
        lst_menu.add(myTagsMenu);
        return lst_menu;
    }


}
