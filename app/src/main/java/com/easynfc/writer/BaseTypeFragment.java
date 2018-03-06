package com.easynfc.writer;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easynfc.MainActivity;
import com.easynfc.R;
import com.easynfc.data.model.WifiAuthType;
import com.easynfc.data.model.WifiTag;
import com.easynfc.util.AppUtils;
import com.easynfc.writer.app_launcher.AppLauncherWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterContract;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseTypeFragment extends Fragment {

    private LayoutInflater inflater;
    private RelativeLayout customDialogView;
    private RelativeLayout aarListView;
    private FrameLayout parentView;
    private MainActivity main;


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
        this.inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customDialogView = (RelativeLayout) this.inflater.inflate(R.layout.writer_dialog, null);
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

    public void showAarList(AppLauncherWriterContract.OnAarItemClickedCallback callback) {
        setAarView(callback);
        parentView.addView(aarListView);
    }

    public void hideDialog() {
        ((ViewGroup) customDialogView.getParent()).removeView(customDialogView);
    }

    public void hideAarList() {
        ((ViewGroup) aarListView.getParent()).removeView(aarListView);
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

    private void setAarView(final AppLauncherWriterContract.OnAarItemClickedCallback callback) {
        aarListView = (RelativeLayout) this.inflater.inflate(R.layout.aar_dialog_list, null);

        ListView list = aarListView.findViewById(R.id.aarlist);

        final Typeface typeface = AppUtils.getAppTypeface(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.aar_item_tv, getInstalledPackageNameList()) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(typeface);
                return v;
            }
        };

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int row, long l) {
                Object obj = adapterView.getItemAtPosition(row);
                callback.OnSuccess(obj.toString());
                hideAarList();
            }
        });

        TextView txtTitle = aarListView.findViewById(R.id.txtDialogTitle);
        Button btnCustom = aarListView.findViewById(R.id.btn_custom_aar_list);
        txtTitle.setTypeface(typeface);
        btnCustom.setTypeface(typeface);
        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAarList();
            }
        });
    }


    private List<String> getInstalledPackageNameList() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = getActivity().getPackageManager().queryIntentActivities(mainIntent, 0);

        List<String> list = new ArrayList<>();
        for (ResolveInfo item : pkgAppsList) {

            list.add(item.activityInfo.packageName);
            String currentHomePackage = item.activityInfo.packageName;
            Log.v("app", "" + currentHomePackage);
        }

        return list;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                main.navigateToTagsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
