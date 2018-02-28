package com.easynfc.writer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.easynfc.R;
import com.easynfc.util.AppConstants;
import com.easynfc.util.NfcUtils;
import com.easynfc.writer.simple_text.SimpleTextWriterFragment;
import com.easynfc.writer.simple_text.SimpleTextWriterPresenter;
import com.easynfc.writer.url.UrlWriterFragment;
import com.easynfc.writer.url.UrlWriterPresenter;

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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof BaseTypeFragment){
            ((BaseTypeFragment) fragment).processNfc(intent);
        }
    }

    private void opentTypeFragment(int typeWriter) {
        switch (typeWriter) {
            case AppConstants.SIMPLE_TEXT_TAG:
                navigateToSimpleTextWriter();
                break;
            case AppConstants.URL_TAG:
                navigateToUrlWriter();
                break;
            case AppConstants.SMS_TAG:
                navigateToSmsWriter();
                break;
            case AppConstants.PHONE_TAG:
                navigateToPhone();
                break;
            case AppConstants.APP_LAUNCHER_TAG:
                navigateToAppLauncherWriter();
                break;
            case AppConstants.LOCATION_TAG:
                navigateToLocationWriter();
                break;
            case AppConstants.WIFI_TAG:
                navigateToWifiWriter();
                break;
            case AppConstants.EMAIL_TAG:
                navigateToEmailWriter();
                break;
            case AppConstants.NDEF_FORMAT_TAG:
                navigateToFormatWriter();
                break;
            case AppConstants.LOCK_TAG:
                navigateToLockWriter();
                break;
            default:
        }
    }

    private void navigateToSimpleTextWriter() {
        SimpleTextWriterFragment simpleTextWriterFragment = (SimpleTextWriterFragment) getSupportFragmentManager().findFragmentByTag(SimpleTextWriterFragment.TAG);
        if (simpleTextWriterFragment == null) {
            simpleTextWriterFragment = SimpleTextWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, simpleTextWriterFragment)
                    .commit();
        }

       NfcUtils nfcUtils = NfcUtils.getInstance(this);
        SimpleTextWriterPresenter presenter = new SimpleTextWriterPresenter(simpleTextWriterFragment, nfcUtils);
    }

    private void navigateToUrlWriter() {
        UrlWriterFragment urlWriterFragment = (UrlWriterFragment) getSupportFragmentManager().findFragmentByTag(UrlWriterFragment.TAG);
        if (urlWriterFragment == null) {
            urlWriterFragment = UrlWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, urlWriterFragment)
                    .commit();
        }

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        UrlWriterPresenter presenter = new UrlWriterPresenter(urlWriterFragment, nfcUtils);
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
