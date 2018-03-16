package com.easynfc.util.custom;


import android.content.Context;
import android.util.AttributeSet;

import com.easynfc.R;
import com.easynfc.util.AppUtils;

/**
 * Created by pablorojas on 6/3/18.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {


    public CustomTextView(Context context) {
        super(context);
        AppUtils.setTypeface(getContext(), this);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        AppUtils.setTypeface(getContext(), this);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        AppUtils.setTypeface(getContext(), this);
    }


}
