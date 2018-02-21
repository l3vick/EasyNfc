package com.easynfc.writer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import com.easynfc.R;
import com.easynfc.mytags.MyTagsFragment;
import com.easynfc.util.AppConstants;
import com.easynfc.writer.SimpleText.SimpleTextWriterFragment;

public class WriterActivity extends AppCompatActivity {

    private static final String TYPE_WRITER = "TYPE_WRITER";

    public static Intent newIntent(Context context, int typeWriter) {
        Intent intent = new Intent(context, WriterActivity.class);
        intent.putExtra(TYPE_WRITER, typeWriter);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        opentTypeFragment(getIntent().getIntExtra(TYPE_WRITER, 0));
    }

    private void opentTypeFragment(int typeWriter) {
        switch (typeWriter) {
            case AppConstants.SIMPLE_TEXT_TAG:
                navigateToSimpleTextWriter();
            case AppConstants.URL_TAG:
                navigateToUrlWriter();
            case AppConstants.SMS_TAG:
                navigateToSmsWriter();
            case AppConstants.PHONE_TAG:
                navigateToPhone();
            case AppConstants.APP_LAUNCHER_TAG:
                navigateToAppLauncherWriter();
            case AppConstants.LOCATION_TAG:
                navigateToLocationWriter();
            case AppConstants.WIFI_TAG:
                navigateToWifiWriter();
            case AppConstants.EMAIL_TAG:
                navigateToEmailWriter();
            case AppConstants.NDEF_FORMAT_TAG:
                navigateToFormatWriter();
            case AppConstants.LOCK_TAG:
                navigateToLockWriter();
                default:
        }
    }

    private void navigateToSimpleTextWriter() {
         SimpleTextWriterFragment simpleTextWriterFragment= (SimpleTextWriterFragment) getSupportFragmentManager().findFragmentByTag(SimpleTextWriterFragment.TAG);
        if (simpleTextWriterFragment == null) {
            simpleTextWriterFragment = SimpleTextWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, simpleTextWriterFragment)
                    .commit();
        }
    }

    private void navigateToUrlWriter() {
    }

    private void navigateToSmsWriter() {
    }

    private void navigateToPhone() {
    }

    private void navigateToAppLauncherWriter() {
    }

    private void navigateToLocationWriter() {
    }

    private void navigateToWifiWriter() {
    }

    private void navigateToEmailWriter() {
    }

    private void navigateToFormatWriter() {
    }

    private void navigateToLockWriter() {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
