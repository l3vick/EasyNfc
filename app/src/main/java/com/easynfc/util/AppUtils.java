package com.easynfc.util;

import android.app.Activity;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;

/**
 * Created by pablorojas on 19/2/18.
 */

public class AppUtils {

    private static Activity context;

    public static DisplayMetrics getDisplayMetrics(Activity activity){
        context = activity;
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.heightPixels = metrics.heightPixels - getStatusActionBarHeight();
        return metrics;
    }

    private static int getStatusActionBarHeight() {
        // status bar height
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        // action bar height
        int actionBarHeight = 0;
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize }
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return statusBarHeight + actionBarHeight;
    }
}
