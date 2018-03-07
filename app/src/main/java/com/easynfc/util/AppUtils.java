package com.easynfc.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.widget.TextView;

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


    public static void setTypeface(Context context, TextView textview) {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "exo2.ttf");
        }
        textview.setTypeface(typeface);
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

}
