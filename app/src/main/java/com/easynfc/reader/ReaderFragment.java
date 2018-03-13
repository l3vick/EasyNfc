package com.easynfc.reader;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easynfc.BaseFragment;
import com.easynfc.R;
import com.easynfc.data.model.TagData;
import com.easynfc.util.AppConstants;

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
        customDialogView = (RelativeLayout) inflater.inflate(R.layout.writer_dialog, null);
        Button cancelBtn = customDialogView.findViewById(R.id.btn_cancel_dialog);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDialog();
            }
        });
        showDialog();
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
        hideDialog();
        txtType.setText(tagData.getType());
        
        if (tagData.getTechList().length >= 3) {
            txtTag.setText(tagData.getTechList()[0].split("android.nfc.tech.")[1] + ", " + tagData.getTechList()[1].split("android.nfc.tech.")[1]);
            txtTech.setText(tagData.getTechList()[2].split("android.nfc.tech.")[1]);
        }else{
            if (tagData.getTechList().length == 2) {
                txtTag.setText(tagData.getTechList()[0].split("android.nfc.tech.")[1]);
                txtTech.setText(tagData.getTechList()[1].split("android.nfc.tech.")[1]);
            }
            if (tagData.getTechList().length == 1) {
                txtTag.setText(tagData.getTechList()[0].split("android.nfc.tech.")[1]);
            }
        }
        txtRtd.setText(tagData.getRtd());
        txtTnf.setText(tagData.getTnf());
        txtSize.setText(tagData.getSize());
        txtContent.setText(tagData.getContent());
        imgType.setImageResource(0);
        imgType.setImageResource(getResourceByType(tagData.getType()));

    }

    private int getResourceByType(String type) {
        switch (type) {
            case AppConstants.TEXT:
                return R.drawable.ic_text_eb_superlight;
            case AppConstants.URL:
                return R.drawable.ic_url_eb_superlight;
            case AppConstants.SMS:
                return R.drawable.ic_sms_eb_superlight;
            case AppConstants.PHONE:
                return R.drawable.ic_phone_eb_superlight;
            case AppConstants.AAR:
                return R.drawable.ic_aar_eb_superlight;
            case AppConstants.LOCATION:
                return R.drawable.ic_location_eb_superlight;
            case AppConstants.WIFI:
                return R.drawable.ic_wifi_eb_superlight;
            case AppConstants.EMAIL:
                return R.drawable.ic_email_eb_superlight;
            default:
                return R.drawable.ic_nfc_eb_ghost;
        }
    }


    public void showDialog() {
        parentView.addView(customDialogView);
    }


    public void hideDialog() {
        ((ViewGroup) customDialogView.getParent()).removeView(customDialogView);
    }
}
