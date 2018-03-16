package com.easynfc.util.shared;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

/**
 * Created by pablorojas on 16/3/18.
 */

public class EditTextWatcher implements TextWatcher {

    private Button[] buttons;

    public EditTextWatcher(Button... buttons) {
        this.buttons = buttons;
        for (Button button : this.buttons) {
            button.setEnabled(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count != 0){
            for (Button button : buttons) {
                button.setEnabled(true);
            }
        }else{
            for (Button button : buttons) {
                button.setEnabled(false);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
