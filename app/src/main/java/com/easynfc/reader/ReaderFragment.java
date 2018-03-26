package com.easynfc.reader;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easynfc.BaseFragment;
import com.easynfc.R;
import com.easynfc.data.model.TagResponse;
import com.easynfc.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.parent_view)
    FrameLayout parentView;
    @BindView(R.id.img_type)
    ImageView imgType;

    private RelativeLayout customDialogView;

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
        customDialogView = (RelativeLayout) inflater.inflate(R.layout.reader_dialog, null);
        ImageButton btnCloseDialog = customDialogView.findViewById(R.id.btn_close_reader_dialog);
        btnCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDialog();
            }
        });
        txtContent.setMovementMethod(new ScrollingMovementMethod());
        showDialog(false);
        return v;
    }

    @Override
    public void setPresenter(ReaderContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void setTagData(TagResponse tagResponse) {
        hideDialog();
        txtType.setText(tagResponse.getType());
        
        if (tagResponse.getTechList().length >= 3) {
            txtTag.setText(tagResponse.getTechList()[0].split("android.nfc.tech.")[1] + ", " + tagResponse.getTechList()[1].split("android.nfc.tech.")[1]);
            txtTech.setText(tagResponse.getTechList()[2].split("android.nfc.tech.")[1]);
        }else{
            if (tagResponse.getTechList().length == 2) {
                txtTag.setText(tagResponse.getTechList()[0].split("android.nfc.tech.")[1]);
                txtTech.setText(tagResponse.getTechList()[1].split("android.nfc.tech.")[1]);
            }
            if (tagResponse.getTechList().length == 1) {
                txtTag.setText(tagResponse.getTechList()[0].split("android.nfc.tech.")[1]);
            }
        }
        txtRtd.setText(tagResponse.getRtd());
        txtTnf.setText(tagResponse.getTnf());
        txtSize.setText(tagResponse.getSize());
        txtContent.setText(tagResponse.getContent());
        imgType.setImageResource(0);
        imgType.setImageResource(AppUtils.getResourceByType(tagResponse.getType()));

    }

    @OnClick(R.id.btn_reload_reader)
    void OnReloadReaderClick () {
        showDialog(true);
    }


    public void showDialog(boolean animated) {
        if (animated){
            customDialogView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        }
        parentView.addView(customDialogView);
        presenter.enableForegroundDispatch();
    }


    public void hideDialog() {
        customDialogView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
        ((ViewGroup) customDialogView.getParent()).removeView(customDialogView);
    }

}
