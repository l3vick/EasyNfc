package com.easynfc;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.easynfc.data.source.TagsRepository;
import com.easynfc.data.source.local.tags.TagsLocalDataSource;
import com.easynfc.menu.MenuFragment;
import com.easynfc.menu.MenuPresenter;
import com.easynfc.mytags.MyTagsFragment;
import com.easynfc.mytags.MyTagsPresenter;
import com.easynfc.reader.ReaderFragment;
import com.easynfc.reader.ReaderPresenter;
import com.easynfc.tagsmenu.TagsMenuFragment;
import com.easynfc.tagsmenu.TagsMenuPresenter;
import com.easynfc.util.AppConstants;
import com.easynfc.util.NfcUtils;
import com.easynfc.writer.BaseTypeFragment;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigateToMenu(false);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof BaseTypeFragment) {
            ((BaseTypeFragment) fragment).processNfc(intent);
        } else if (fragment instanceof ReaderFragment) {
            ((ReaderFragment) fragment).processNfc(intent);
        }
    }

    public void navigateToMenu(boolean animated) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag(MenuFragment.TAG);
        if (menuFragment == null) {
            menuFragment = MenuFragment.newInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (animated) {
                transaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
            }

            transaction.replace(R.id.container, menuFragment)
                    .commit();
        }

        MenuPresenter presenter = new MenuPresenter(menuFragment);
    }

    public void navigateToTagsMenu(boolean reverseAnimation) {
        TagsMenuFragment tagsMenuFragment = (TagsMenuFragment) getSupportFragmentManager().findFragmentByTag(TagsMenuFragment.TAG);
        if (tagsMenuFragment == null) {
            tagsMenuFragment = TagsMenuFragment.newInstance();

            final FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            if (reverseAnimation) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
            }
            fragmentTransaction.replace(R.id.container, tagsMenuFragment)
                    .commit();
        }

        TagsMenuPresenter presenter = new TagsMenuPresenter(tagsMenuFragment);
    }

    public void navigateToTagReader() {
        ReaderFragment readerFragment = (ReaderFragment) getSupportFragmentManager().findFragmentByTag(ReaderFragment.TAG);
        if (readerFragment == null) {
            readerFragment = ReaderFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, readerFragment)
                    .commit();
        }
        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        ReaderPresenter presenter = new ReaderPresenter(readerFragment, nfcUtils);
    }

    public void navigateToMyTags(boolean reverseAnimation) {
        MyTagsFragment myTagsFragment = (MyTagsFragment) getSupportFragmentManager().findFragmentByTag(MyTagsFragment.TAG);
        if (myTagsFragment == null) {
            myTagsFragment = MyTagsFragment.newInstance();


            final FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            if (reverseAnimation) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
            }

            fragmentTransaction.replace(R.id.container, myTagsFragment)
                    .commit();
        }

        TagsLocalDataSource tagsLocalDataSource = TagsLocalDataSource.getInstance(this);

        TagsRepository tagsRepository = TagsRepository.getInstance(tagsLocalDataSource);

        MyTagsPresenter presenter = new MyTagsPresenter(myTagsFragment, tagsRepository);

    }

    public void navigateToWriter(AppConstants.TagTypes tagTypes, long tagId) {
        switch (tagTypes) {
            case TEXT:
                navigateToSimpleTextWriter(tagId);
                break;
            case URL:
                navigateToUrlWriter(tagId);
                break;
            case SMS:
                navigateToSmsWriter(tagId);
                break;
            case PHONE:
                navigateToPhone(tagId);
                break;
            case AAR:
                navigateToAppLauncherWriter(tagId);
                break;
            case LOCATION:
                navigateToLocationWriter(tagId);
                break;
            case WIFI:
                navigateToWifiWriter(tagId);
                break;
            case EMAIL:
                navigateToEmailWriter(tagId);
                break;
            case NDEF:
                navigateToFormatWriter();
                break;
            case LOCK:
                navigateToLockWriter();
                break;
            default:
        }

    }

    private void navigateToSimpleTextWriter(long tagId) {
        SimpleTextWriterFragment simpleTextWriterFragment = (SimpleTextWriterFragment) getSupportFragmentManager().findFragmentByTag(SimpleTextWriterFragment.TAG);
        if (simpleTextWriterFragment == null) {
            simpleTextWriterFragment = SimpleTextWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, simpleTextWriterFragment)
                    .commit();
        }

        TagsLocalDataSource tagsLocalDataSource = TagsLocalDataSource.getInstance(this);

        TagsRepository tagsRepository = TagsRepository.getInstance(tagsLocalDataSource);

        NfcUtils nfcUtils = NfcUtils.getInstance(this);

        SimpleTextWriterPresenter presenter = new SimpleTextWriterPresenter(simpleTextWriterFragment, nfcUtils, tagsRepository);

        if (tagId != 0) {
            simpleTextWriterFragment.setTag(tagId);
        }
    }

    private void navigateToUrlWriter(long tagId) {
        UrlWriterFragment urlWriterFragment = (UrlWriterFragment) getSupportFragmentManager().findFragmentByTag(UrlWriterFragment.TAG);
        if (urlWriterFragment == null) {
            urlWriterFragment = UrlWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, urlWriterFragment)
                    .commit();
        }


        TagsLocalDataSource tagsLocalDataSource = TagsLocalDataSource.getInstance(this);

        TagsRepository tagsRepository = TagsRepository.getInstance(tagsLocalDataSource);

        NfcUtils nfcUtils = NfcUtils.getInstance(this);

        UrlWriterPresenter presenter = new UrlWriterPresenter(urlWriterFragment, nfcUtils, tagsRepository);

        if (tagId != 0) {
            urlWriterFragment.setTag(tagId);
        }
    }

    private void navigateToSmsWriter(long tagId) {
        SmsWriterFragment smsWriterFragment = (SmsWriterFragment) getSupportFragmentManager().findFragmentByTag(SmsWriterFragment.TAG);
        if (smsWriterFragment == null) {
            smsWriterFragment = SmsWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, smsWriterFragment)
                    .commit();
        }

        TagsLocalDataSource tagsLocalDataSource = TagsLocalDataSource.getInstance(this);

        TagsRepository tagsRepository = TagsRepository.getInstance(tagsLocalDataSource);

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        SmsWriterPresenter presenter = new SmsWriterPresenter(smsWriterFragment, nfcUtils, tagsRepository);

        if (tagId != 0) {
            smsWriterFragment.setTag(tagId);
        }
    }

    private void navigateToPhone(long tagId) {
        PhoneWriterFragment phoneWriterFragment = (PhoneWriterFragment) getSupportFragmentManager().findFragmentByTag(PhoneWriterFragment.TAG);
        if (phoneWriterFragment == null) {
            phoneWriterFragment = PhoneWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, phoneWriterFragment)
                    .commit();
        }
        TagsLocalDataSource tagsLocalDataSource = TagsLocalDataSource.getInstance(this);

        TagsRepository tagsRepository = TagsRepository.getInstance(tagsLocalDataSource);

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        PhoneWriterPresenter presenter = new PhoneWriterPresenter(phoneWriterFragment, nfcUtils, tagsRepository);


        if (tagId != 0) {
            phoneWriterFragment.setTag(tagId);
        }
    }

    private void navigateToAppLauncherWriter(long tagId) {
        AppLauncherWriterFragment appLauncherWriterFragment = (AppLauncherWriterFragment) getSupportFragmentManager().findFragmentByTag(AppLauncherWriterFragment.TAG);
        if (appLauncherWriterFragment == null) {
            appLauncherWriterFragment = AppLauncherWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, appLauncherWriterFragment)
                    .commit();
        }
        TagsLocalDataSource tagsLocalDataSource = TagsLocalDataSource.getInstance(this);

        TagsRepository tagsRepository = TagsRepository.getInstance(tagsLocalDataSource);

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        AppLauncherWriterPresenter presenter = new AppLauncherWriterPresenter(appLauncherWriterFragment, nfcUtils, tagsRepository);


        if (tagId != 0) {
            appLauncherWriterFragment.setTag(tagId);
        }
    }

    private void navigateToLocationWriter(long tagId) {
        LocationWriterFragment locationWriterFragment = (LocationWriterFragment) getSupportFragmentManager().findFragmentByTag(LocationWriterFragment.TAG);
        if (locationWriterFragment == null) {
            locationWriterFragment = LocationWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, locationWriterFragment)
                    .commit();
        }
        TagsLocalDataSource tagsLocalDataSource = TagsLocalDataSource.getInstance(this);

        TagsRepository tagsRepository = TagsRepository.getInstance(tagsLocalDataSource);

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        LocationWriterPresenter presenter = new LocationWriterPresenter(locationWriterFragment, nfcUtils, tagsRepository);

        if (tagId != 0) {
            locationWriterFragment.setTag(tagId);
        }
    }

    private void navigateToWifiWriter(long tagId) {
        WiFiWriterFragment wiFiWriterFragment = (WiFiWriterFragment) getSupportFragmentManager().findFragmentByTag(WiFiWriterFragment.TAG);
        if (wiFiWriterFragment == null) {
            wiFiWriterFragment = WiFiWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, wiFiWriterFragment)
                    .commit();
        }
        TagsLocalDataSource tagsLocalDataSource = TagsLocalDataSource.getInstance(this);

        TagsRepository tagsRepository = TagsRepository.getInstance(tagsLocalDataSource);

        NfcUtils nfcUtils = NfcUtils.getInstance(this);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WiFiWriterPresenter presenter = new WiFiWriterPresenter(wiFiWriterFragment, nfcUtils, wifiManager, tagsRepository);


        if (tagId != 0) {
            wiFiWriterFragment.setTag(tagId);
        }
    }

    private void navigateToEmailWriter(long tagId) {
        EmailWriterFragment emailWriterFragment = (EmailWriterFragment) getSupportFragmentManager().findFragmentByTag(EmailWriterFragment.TAG);
        if (emailWriterFragment == null) {
            emailWriterFragment = EmailWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, emailWriterFragment)
                    .commit();
        }
        TagsLocalDataSource tagsLocalDataSource = TagsLocalDataSource.getInstance(this);

        TagsRepository tagsRepository = TagsRepository.getInstance(tagsLocalDataSource);

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        EmailWriterPresenter presenter = new EmailWriterPresenter(emailWriterFragment, nfcUtils, tagsRepository);


        if (tagId != 0) {
            emailWriterFragment.setTag(tagId);
        }
    }

    private void navigateToFormatWriter() {
        FormatWriterFragment formatWriterFragment = (FormatWriterFragment) getSupportFragmentManager().findFragmentByTag(FormatWriterFragment.TAG);
        if (formatWriterFragment == null) {
            formatWriterFragment = FormatWriterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
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
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, lockWriterFragment)
                    .commit();
        }

        NfcUtils nfcUtils = NfcUtils.getInstance(this);
        LockWriterPresenter presenter = new LockWriterPresenter(lockWriterFragment, nfcUtils);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().
                INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null){
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return true;
    }
}
