package com.easynfc.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.easynfc.R;

/**
 * Created by pablorojas on 19/2/18.
 */

public class AppUtils {

    private static Typeface typeface = null;


    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.heightPixels = metrics.heightPixels - getStatusActionBarHeight(activity);
        return metrics;
    }

    public static Typeface getAppTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "exo2.ttf");
    }


    public static void setTypeface(Context context, TextView... textviews) {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "exo2.ttf");
        }
        for (TextView textview : textviews) {
            textview.setTypeface(typeface);
        }

    }

    private static int getStatusActionBarHeight(Activity activity) {
        // status bar height
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        // action bar height
        int actionBarHeight = 0;
        final TypedArray styledAttributes = activity.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize}
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return statusBarHeight + actionBarHeight;
    }


    public static int getResourceByType(String type) {
        AppConstants.TagTypes tagTypes = AppConstants.getTypeTag(type);
        switch (tagTypes) {
            case TEXT:
                return R.drawable.ic_text_eb_superlight;
            case URL:
                return R.drawable.ic_url_eb_superlight;
            case SMS:
                return R.drawable.ic_sms_eb_superlight;
            case PHONE:
                return R.drawable.ic_phone_eb_superlight;
            case AAR:
                return R.drawable.ic_aar_eb_superlight;
            case LOCATION:
                return R.drawable.ic_location_eb_superlight;
            case WIFI:
                return R.drawable.ic_wifi_eb_superlight;
            case EMAIL:
                return R.drawable.ic_email_eb_superlight;
            default:
                return R.drawable.ic_nfc_eb_ghost;
        }
    }
}
