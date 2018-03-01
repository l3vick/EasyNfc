package com.easynfc.writer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.easynfc.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseTypeFragment extends Fragment {

    private LayoutInflater inflater;
    private RelativeLayout customDialogView;
    private FrameLayout parentView;

    public BaseTypeFragment() {
        // Required empty public constructors
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customDialogView = (RelativeLayout) this.inflater.inflate(R.layout.custom_dialog, null);
        Button cancelBtn = customDialogView.findViewById(R.id.btn_cancel_dialog);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDialog();
            }
        });
    }

    public void processNfc(Intent intent) {

    }


    public void setParentView(View view) {
        parentView = view.findViewById(R.id.parentView);
    }

    public void showDialog() {
        parentView.addView(customDialogView);
    }

    public void hideDialog() {
        ((ViewGroup) customDialogView.getParent()).removeView(customDialogView);
    }

    public void tagWritten() {
        Snackbar snackbar = Snackbar.make(parentView, R.string.written_succesfull, Snackbar.LENGTH_SHORT);
        snackbar.show();

        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                hideDialog();
            }

            @Override
            public void onShown(Snackbar snackbar) {

            }
        });


    }
}
