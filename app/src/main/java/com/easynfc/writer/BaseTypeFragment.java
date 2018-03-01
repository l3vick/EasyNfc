package com.easynfc.writer;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.easynfc.R;
import com.easynfc.writer.app_launcher.AppLauncherWriterContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseTypeFragment extends Fragment {

    private LayoutInflater inflater;
    private RelativeLayout customDialogView;
    private RelativeLayout aarListView;
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
        aarListView =  (RelativeLayout) this.inflater.inflate(R.layout.aar_list, null);

        ListView list = aarListView.findViewById(R.id.aarlist);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.aar_item_tv, getInstalledPackageNameList());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int row, long l) {
                Object obj = adapterView.getItemAtPosition(row);
                callback.OnSuccess(obj.toString());
                hideAarList();
            }
        });

        Button btnCustom = aarListView.findViewById(R.id.btn_custom_aar_list);
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


}
