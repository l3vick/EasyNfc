package com.easynfc.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.util.Log;

import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;


/**
 * Created by pablorojas on 26/2/18.
 */

public class NfcUtils {

    private Activity activity;
    private NfcAdapter nfcAdapter;
    private static NfcUtils INSTANCE = null;

    public static NfcUtils getInstance(Activity activity) {
        if (INSTANCE == null) {
            INSTANCE = new NfcUtils(activity);
        }
        INSTANCE.setActivity(activity);
        return INSTANCE;
    }

    public NfcUtils(Activity activity) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public boolean isNfcEnabledDevice() {
        boolean hasFeature = activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC);
        boolean isEnabled = (nfcAdapter != null && nfcAdapter.isEnabled());
        return hasFeature && isEnabled;
    }


    public void enableForegroundDispatch() {
        if (isNfcEnabledDevice()) {
            Intent intent = new Intent(activity, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);

            IntentFilter[] intentFilter = new IntentFilter[]{};

            String[][] techList = new String[][]{{Ndef.class.getName()}, {NdefFormatable.class.getName()}};

            nfcAdapter.enableForegroundDispatch(activity, pendingIntent, intentFilter, techList);
        }

    }

    public void disableForegroundDispatch() {
        if (isNfcEnabledDevice()) {
            nfcAdapter.disableForegroundDispatch(activity);
        }

    }

    public boolean isNfcIntent(Intent intent) {
        return intent.hasExtra(NfcAdapter.EXTRA_TAG);
    }

    public void writeSimpleTextTag(Intent intent, String text, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        NdefRecord uriRecord = createTextRecord(text);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{uriRecord});
        writeNdefMessage(intent, ndefMessage, callback);
    }

    public void writeUrlTag(Intent intent, String text, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        NdefRecord uriRecord = createUriRecord(text);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{uriRecord});
        writeNdefMessage(intent, ndefMessage, callback);
    }

    public void writeSmsTag(Intent intent, String number, String text, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        String smsUri = String.format("sms:%s?body=%s", number, URLEncoder.encode(text));
        NdefRecord uriRecord = createUriRecord(smsUri);
        NdefMessage ndefMsg =  new NdefMessage(new NdefRecord[]{uriRecord});
        writeNdefMessage(intent, ndefMsg, callback);
    }


    public void writeNdefMessage(Intent intent, NdefMessage ndefMessage, TagWrittenCallback callback) throws IOException, FormatException, ReadOnlyTagException, InsufficientSizeException, NdefFormatException {

        Tag tag = getTagFromIntent(intent);

        Ndef ndef = Ndef.get(tag);

        if (ndef == null) {
            formatTag(tag, ndefMessage);

        } else {

            ndef.connect();

            int maxSize = ndef.getMaxSize();
            int messageSize = ndefMessage.toByteArray().length;

            if (!ndef.isWritable()) {

                throw new ReadOnlyTagException();

            } else if (maxSize < messageSize) {

                throw new InsufficientSizeException(messageSize, maxSize);

            } else {
                ndef.writeNdefMessage(ndefMessage);
                ndef.close();
                callback.OnSuccess();
            }

        }
    }

    public Tag getTagFromIntent(Intent intent) {
        return ((Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG));
    }


    private void formatTag(Tag tag, NdefMessage ndefMessage) throws IOException, FormatException, NdefFormatException {

        NdefFormatable ndefFormat = NdefFormatable.get(tag);

        if (ndefFormat == null) {
            throw new NdefFormatException();
        }

        ndefFormat.connect();
        ndefFormat.format(ndefMessage);
        ndefFormat.close();
    }

    public NdefRecord createTextRecord(String content) {
        try {
            byte[] language = Locale.getDefault().getLanguage().getBytes("UTF-8");

            final byte[] text = content.getBytes("UTF-8");

            final int languageSize = language.length;
            final int textLength = text.length;

            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + languageSize + textLength);

            payload.write((byte) (languageSize & 0x1F));
            payload.write(language, 0, languageSize);
            payload.write(text, 0, textLength);

            return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());

        } catch (UnsupportedEncodingException e) {
            Log.e("createTextRecord", e.getMessage());
        }
        return null;
    }


    public NdefRecord createUriRecord(String uri) {

        NdefRecord rtdUriRecord = null;

        try {

            byte[] uriField;

            uriField = uri.getBytes("UTF-8");

            byte[] payload = new byte[uriField.length + 1];
            payload[0] = 0x00;

            System.arraycopy(uriField, 0, payload, 1, uriField.length);

            rtdUriRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_URI, new byte[0], payload);

        } catch (UnsupportedEncodingException e) {
            Log.e("createUriRecord", e.getMessage());
        }

        return rtdUriRecord;
    }

    public interface TagWrittenCallback {
        void OnSuccess();

        void OnError();
    }
}
