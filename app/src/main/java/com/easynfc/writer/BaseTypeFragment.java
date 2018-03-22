package com.easynfc.writer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.easynfc.MainActivity;
import com.easynfc.R;
import com.easynfc.util.AppUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseTypeFragment extends Fragment {

    private RelativeLayout customDialogView, emulateDialogView;
    private FrameLayout parentView;
    private MainActivity main;
    public long tagId = 0;


    public BaseTypeFragment() {
        // Required empty public constructors
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        main = (MainActivity) getActivity();
        setHasOptionsMenu(true);
        LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customDialogView = (RelativeLayout) inflater.inflate(R.layout.writer_dialog, null);
        emulateDialogView = (RelativeLayout) inflater.inflate(R.layout.emulate_dialog, null);
        ImageButton closeWriterBtn = customDialogView.findViewById(R.id.btn_close_writer_dialog);
        closeWriterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDialog();
            }
        });
        ImageButton closeEmulateBtn = emulateDialogView.findViewById(R.id.btn_close_emulate_dialog);
        closeEmulateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideEmulateDialog();
            }
        });

    }

    public void processNfc(Intent intent) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setTag(long timestamp) {
        tagId = timestamp;
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (tagId != 0 ){
                    main.navigateToMyTags(true);
                } else {
                    main.navigateToTagsMenu(true);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void onAnyTextChanged(int count);

    public void showAddedSuccess() {
        Snackbar snackbar = Snackbar.make(parentView, R.string.tag_saved, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void showMessageError() {
        Snackbar snackbar = Snackbar.make(parentView, R.string.tag_saved_error, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void showEmulateDialog() {
        parentView.addView(emulateDialogView);
    }

    private void hideEmulateDialog() {
        ((ViewGroup) emulateDialogView.getParent()).removeView(emulateDialogView);
    }
}
