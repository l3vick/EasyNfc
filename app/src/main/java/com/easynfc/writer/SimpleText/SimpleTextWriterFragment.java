package com.easynfc.writer.SimpleText;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easynfc.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleTextWriterFragment extends Fragment {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_input_title)
    TextView txtInputTitle;



    public static final String TAG = "SimpleTextWriterFragment";

    public SimpleTextWriterFragment() {
        // Required empty public constructor
    }

    public static SimpleTextWriterFragment newInstance(){
        return new SimpleTextWriterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_simple_text_writer, container, false);
        ButterKnife.bind(this,v);
        Typeface exo2 = Typeface.createFromAsset(getContext().getAssets(), "exo2.ttf");
        txtTitle.setTypeface(exo2);
        txtInputTitle.setTypeface(exo2);
        return v;
    }

}
