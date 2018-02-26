package com.easynfc.writer.SimpleText;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.easynfc.R;
import com.easynfc.writer.BaseTypeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleTextWriterFragment extends Fragment implements SimpleTextWriterContract.View {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_simple_text_title)
    TextView txtInputTitle;
    @BindView(R.id.et_simple_text)
    EditText etSimpleText;
    private SimpleTextWriterContract.Presenter presenter;
    private AlertDialog dialog;



    public static final String TAG = "SimpleTextWriterFragment";

    public SimpleTextWriterFragment() {
        // Required empty public constructor
    }

    public static SimpleTextWriterFragment newInstance() {
        return new SimpleTextWriterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_simple_text_writer, container, false);
        ButterKnife.bind(this, v);
        Typeface exo2 = Typeface.createFromAsset(getContext().getAssets(), "exo2.ttf");
        txtTitle.setTypeface(exo2);
        txtInputTitle.setTypeface(exo2);
        return v;
    }

    @OnClick(R.id.btn_record)
    void onRecordTagBtnPressed() {
        showRecordDialog();
        presenter.enableForegroundDispatch();
    }

    @OnClick(R.id.btn_save)
    void onSaveTagBtnPressed() {

    }

    public void showRecordDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);

        Button onCancelDialog = alertLayout.findViewById(R.id.cancelDialogBtn);
        dialog = alert.create();

        onCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void setPresenter(SimpleTextWriterContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void onPause() {
        super.onPause();
        presenter.disableForegroundDispatch();
    }

    @Override
    public void OnTagWritten() {
        dialog.hide();
    }


    public void processNfc(Intent intent) {
        presenter.writeTag(intent, etSimpleText.getText().toString());
    }
}
