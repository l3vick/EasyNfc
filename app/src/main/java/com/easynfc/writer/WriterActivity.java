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
import com.easynfc.writer.app_launcher.AppLauncherWriterFragment;
import com.easynfc.writer.app_launcher.AppLauncherWriterPresenter;
import com.easynfc.writer.email.EmailWriterFragment;
import com.easynfc.writer.email.EmailWriterPresenter;
import com.easynfc.writer.format.FormatWriterFragment;
import com.easynfc.writer.format.FormatWriterPresenter;
import com.easynfc.writer.location.LocationWriterFragment;
import com.easynfc.writer.location.LocationWriterPresenter;
import com.easynfc.writer.lock.LockWriterFragment;
import com.easynfc.writer.lock.LockWriterPresenter;
import com.easynfc.writer.phone.PhoneWriterFragment;
import com.easynfc.writer.phone.PhoneWriterPresenter;
import com.easynfc.writer.simple_text.SimpleTextWriterFragment;
import com.easynfc.writer.simple_text.SimpleTextWriterPresenter;
import com.easynfc.writer.sms.SmsWriterFragment;
import com.easynfc.writer.sms.SmsWriterPresenter;
import com.easynfc.writer.url.UrlWriterFragment;
import com.easynfc.writer.url.UrlWriterPresenter;
import com.easynfc.writer.wi_fi.WiFiWriterFragment;
import com.easynfc.writer.wi_fi.WiFiWriterPresenter;

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
        SmsWriterFragment smsWriterFragment = (SmsWriterFragment) getSupportFragmentManager().findFragmentByTag(SmsWriterFragment.TAG);
        if (smsWriterFragment == null) {
            smsWriterFragment = SmsWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, smsWriterFragment)
                    .commit();
        }

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        SmsWriterPresenter presenter = new SmsWriterPresenter(smsWriterFragment, nfcUtils);
    }

    private void navigateToPhone() {
        PhoneWriterFragment phoneWriterFragment = (PhoneWriterFragment) getSupportFragmentManager().findFragmentByTag(PhoneWriterFragment.TAG);
        if (phoneWriterFragment == null) {
            phoneWriterFragment = PhoneWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, phoneWriterFragment)
                    .commit();
        }

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        PhoneWriterPresenter presenter = new PhoneWriterPresenter(phoneWriterFragment, nfcUtils);
    }

    private void navigateToAppLauncherWriter() {
        AppLauncherWriterFragment appLauncherWriterFragment = (AppLauncherWriterFragment) getSupportFragmentManager().findFragmentByTag(AppLauncherWriterFragment.TAG);
        if (appLauncherWriterFragment == null) {
            appLauncherWriterFragment = AppLauncherWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, appLauncherWriterFragment)
                    .commit();
        }

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        AppLauncherWriterPresenter presenter = new AppLauncherWriterPresenter(appLauncherWriterFragment, nfcUtils);
    }

    private void navigateToLocationWriter() {
        LocationWriterFragment locationWriterFragment = (LocationWriterFragment) getSupportFragmentManager().findFragmentByTag(LocationWriterFragment.TAG);
        if (locationWriterFragment == null) {
            locationWriterFragment = LocationWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, locationWriterFragment)
                    .commit();
        }

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        LocationWriterPresenter presenter = new LocationWriterPresenter(locationWriterFragment, nfcUtils);
    }

    private void navigateToWifiWriter() {
        WiFiWriterFragment wiFiWriterFragment = (WiFiWriterFragment) getSupportFragmentManager().findFragmentByTag(WiFiWriterFragment.TAG);
        if (wiFiWriterFragment == null) {
            wiFiWriterFragment = WiFiWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, wiFiWriterFragment)
                    .commit();
        }

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        WiFiWriterPresenter presenter = new WiFiWriterPresenter(wiFiWriterFragment, nfcUtils);
    }

    private void navigateToEmailWriter() {
        EmailWriterFragment emailWriterFragment = (EmailWriterFragment) getSupportFragmentManager().findFragmentByTag(EmailWriterFragment.TAG);
        if (emailWriterFragment == null) {
            emailWriterFragment = EmailWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, emailWriterFragment)
                    .commit();
        }

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        EmailWriterPresenter presenter = new EmailWriterPresenter(emailWriterFragment, nfcUtils);
    }

    private void navigateToFormatWriter() {
        FormatWriterFragment formatWriterFragment = (FormatWriterFragment) getSupportFragmentManager().findFragmentByTag(FormatWriterFragment.TAG);
        if (formatWriterFragment == null) {
            formatWriterFragment = FormatWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, formatWriterFragment)
                    .commit();
        }

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        FormatWriterPresenter presenter = new FormatWriterPresenter(formatWriterFragment, nfcUtils);
    }

    private void navigateToLockWriter() {
        LockWriterFragment lockWriterFragment = (LockWriterFragment) getSupportFragmentManager().findFragmentByTag(LockWriterFragment.TAG);
        if (lockWriterFragment == null) {
            lockWriterFragment = LockWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, lockWriterFragment)
                    .commit();
        }

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        LockWriterPresenter presenter = new LockWriterPresenter(lockWriterFragment, nfcUtils);
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
