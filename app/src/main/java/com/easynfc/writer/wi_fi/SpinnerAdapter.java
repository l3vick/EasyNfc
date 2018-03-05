package com.easynfc.writer.wi_fi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easynfc.R;
import com.easynfc.util.AppUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by pablorojas on 2/3/18.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    private String title;

    public SpinnerAdapter(Context context, int resourceId, List<String> securityCypherList, String title) {
        super(context, resourceId, securityCypherList);
        this.title = title;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout v = (LinearLayout) inflater.inflate(R.layout.spinner_header, parent, false);
            TextView tv = v.findViewById(R.id.dropdown_item);
            tv.setTypeface(AppUtils.getAppTypeface(getContext()));
            tv.setText(title);
            return v;

        } else {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TextView v = (TextView) inflater.inflate(R.layout.spinner_item, parent, false);
            TextView tv = v.findViewById(R.id.spinner_dialog_root);
            tv.setText(getItem(position));
            tv.setTypeface(AppUtils.getAppTypeface(getContext()));
            tv.setHeight((int) (tv.getTextSize() * 2));
            return v;
        }


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        ((TextView) v).setTypeface(AppUtils.getAppTypeface(getContext()));
        return v;
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public String getItem(int position) {
        if (position == 0) {
            return title;
        }
        return super.getItem(position - 1);
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }
}
