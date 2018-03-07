package com.easynfc.reader;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easynfc.BaseFragment;
import com.easynfc.R;
import com.easynfc.data.model.TagData;
import com.easynfc.menu.MenuContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReaderFragment extends BaseFragment implements ReaderContract.View {


    @BindView(R.id.txt_type)
    TextView txtType;
    @BindView(R.id.txt_tag)
    TextView txtTag;
    @BindView(R.id.txt_tech)
    TextView txtTech;
    @BindView(R.id.txt_tnf)
    TextView txtTnf;
    @BindView(R.id.txt_rtd)
    TextView txtRtd;
    @BindView(R.id.txt_size)
    TextView txtSize;
    @BindView(R.id.txt_content)
    TextView txtContent;

    private ReaderContract.Presenter presenter;
    public static final String TAG = "ReaderFragment";

    public ReaderFragment() {
        // Required empty public constructor
    }

    public static ReaderFragment newInstance() {
        return new ReaderFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reader, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void setPresenter(ReaderContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.enableForegroundDispatch();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.disableForegroundDispatch();
    }

    @Override
    public void processNfc(Intent intent) {
        presenter.processNfc(intent);
    }

    @Override
    public void setTagData(TagData tagData) {
        txtRtd.setText(tagData.getTypeTag());
        txtType.setText(tagData.getTypeTag());
        txtSize.setText(tagData.getSize());
        txtContent.setText(tagData.getContent());
    }
}
