package com.easynfc.menu;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easynfc.MainActivity;
import com.easynfc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    @BindView(R.id.txtRead)
    TextView writeTxt;
    @BindView(R.id.txtWrite)
    TextView tagsTxt;
    @BindView(R.id.txtTags)
    TextView readTxt;

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
        main = ((MainActivity) getActivity());
        Typeface soho = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.raleway));
        writeTxt.setTypeface(soho);
        readTxt.setTypeface(soho);
        tagsTxt.setTypeface(soho);
        return v;
    }

    @OnClick(R.id.writeBtn)
    void onWriteClick(View view){
        main.navigateToTagWriter();
    }

    @OnClick(R.id.readBtn)
    void onReadClick(View view){
        main.navigateToTagReader();
    }


    @OnClick(R.id.btn_my_tags)
    void onMyTagsClick(View view){
        main.navigateToMyTags();
    }


}
