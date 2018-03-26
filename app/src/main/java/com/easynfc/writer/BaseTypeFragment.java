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
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easynfc.MainActivity;
import com.easynfc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseTypeFragment extends Fragment {

    private RelativeLayout writerDialogView, emulateDialogView;
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
        writerDialogView = (RelativeLayout) inflater.inflate(R.layout.writer_dialog, null);
        emulateDialogView = (RelativeLayout) inflater.inflate(R.layout.emulate_dialog, null);
        ImageButton closeWriterBtn = writerDialogView.findViewById(R.id.btn_close_writer_dialog);
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
        writerDialogView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        parentView.addView(writerDialogView);
    }


    public void hideDialog() {
        writerDialogView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
        ((ViewGroup) writerDialogView.getParent()).removeView(writerDialogView);
    }


    public void tagWritten() {
        Snackbar snackbar = Snackbar.make(parentView, R.string.written_succesfull, Snackbar.LENGTH_SHORT);
        setStyle(snackbar);
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

    public void showInserted() {
        Snackbar snackbar = Snackbar.make(parentView, R.string.tag_saved, Snackbar.LENGTH_LONG);
        setStyle(snackbar);
        snackbar.show();
    }

    public void showUpdated() {
        Snackbar snackbar = Snackbar.make(parentView, R.string.tag_updated, Snackbar.LENGTH_LONG);
        setStyle(snackbar);
        snackbar.show();
    }

    public void showMessageError() {
        Snackbar snackbar = Snackbar.make(parentView, R.string.tag_saved_error, Snackbar.LENGTH_LONG);
        setStyle(snackbar);
        snackbar.show();
    }

    public void showEmulateDialog() {
        emulateDialogView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        parentView.addView(emulateDialogView);
    }

    private void hideEmulateDialog() {
        emulateDialogView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
        ((ViewGroup) emulateDialogView.getParent()).removeView(emulateDialogView);
    }

    private void setStyle(Snackbar snackbar) {
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.easy_black_ghost));
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.easy_black_dark));
    }
}
