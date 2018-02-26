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
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;

import com.easynfc.BuildConfig;
import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.data.model.WifiNetwork;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by pablorojas on 27/10/17.
 */

public class NfcUtilsOld {

    private Activity activity;
    private NfcAdapter nfcAdapter;
    private NfcManagerResponse nfcManagerResponse;
    private NfcManagerTagWritten nfcManagerTagWritten;

    private static final String TAG = NfcUtilsOld.class.getSimpleName();

    private static final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    public static final String NFC_TOKEN_MIME_TYPE = "application/vnd.wfa.wsc";
    /*
     * ID into configuration record for SSID and Network Key in hex.
     * Obtained from WFA Wi-Fi Simple Configuration Technical Specification v2.0.5.
     */
    public static final short CREDENTIAL_FIELD_ID = 0x100e;

    public static final short NETWORK_INDEX_FIELD_ID = 0x1026;
    public static final byte NETWORK_INDEX_DEFAULT_VALUE = (byte) 0x01;

    public static final short SSID_FIELD_ID = 0x1045;

    public static final short AUTH_TYPE_FIELD_ID = 0x1003;
    public static final short AUTH_TYPE_EXPECTED_SIZE = 2;
    public static final short AUTH_TYPE_OPEN = 0x0001;
    public static final short AUTH_TYPE_WPA_PSK = 0x0002;
    public static final short AUTH_TYPE_WPA_EAP = 0x0008;
    public static final short AUTH_TYPE_WPA2_EAP = 0x0010;
    public static final short AUTH_TYPE_WPA2_PSK = 0x0020;

    public static final short ENC_TYPE_FIELD_ID = 0x100f;
    public static final short ENC_TYPE_NONE = 0x0001;
    public static final short ENC_TYPE_WEP = 0x0002; // deprecated
    public static final short ENC_TYPE_TKIP = 0x0004; // deprecated -> only with mixed mode (0x000c)
    public static final short ENC_TYPE_AES = 0x0008; // includes CCMP and GCMP
    public static final short ENC_TYPE_AES_TKIP = 0x000c; // mixed mode

    public static final short NETWORK_KEY_FIELD_ID = 0x1027;
    // WPA2-personal (passphrase): 8-63 ASCII characters
    // WPA2-personal: 64 hex characters

    public static final short MAC_ADDRESS_FIELD_ID = 0x1020;

    public static final int MAX_SSID_SIZE_BYTES = 32;
    public static final int MAX_MAC_ADDRESS_SIZE_BYTES = 6;
    public static final int MAX_NETWORK_KEY_SIZE_BYTES = 64;


    public NfcUtilsOld(Activity activity, NfcManagerResponse nfcManagerResponse) {
        this.activity = activity;
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        this.nfcManagerResponse = nfcManagerResponse;
    }

    public NfcUtilsOld(Activity activity, NfcManagerTagWritten nfcManagerTagWritten) {
        this.activity = activity;
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        this.nfcManagerTagWritten = nfcManagerTagWritten;
    }


    public NfcUtilsOld(Activity activity) {
        this.activity = activity;
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
    }



    public boolean isNfcEnabledDevice() {
        boolean hasFeature = activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC);
        boolean isEnabled = (nfcAdapter != null && nfcAdapter.isEnabled());
        return hasFeature && isEnabled;
    }

    public boolean isNfcIntent(Intent intent) {
        return intent.hasExtra(NfcAdapter.EXTRA_TAG);
    }

    public Tag getTagFromIntent(Intent intent) {
        return ((Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG));
    }

    public NdefMessage getNdefMessageFromIntent(Intent intent) {

        NdefMessage ndefMessage = null;

        Parcelable[] extra = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (extra != null && extra.length > 0) {
            ndefMessage = (NdefMessage) extra[0];
        }

        return ndefMessage;

    }

    public NdefMessage getNdefMessageFromTag(Tag tag) {

        NdefMessage ndefMessage = null;
        Ndef ndef = Ndef.get(tag);

        if (ndef != null) {
            ndefMessage = ndef.getCachedNdefMessage();
        }

        return ndefMessage;
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

    public boolean writeNdefMessage(Intent intent, NdefMessage ndefMessage) throws IOException, FormatException, ReadOnlyTagException, InsufficientSizeException, NdefFormatException {

        Tag tag = getTagFromIntent(intent);

        boolean writeSuccessfull = writeNdefMessage(tag, ndefMessage);
        return writeSuccessfull;
    }

    private boolean writeNdefMessage(Tag tag, NdefMessage ndefMessage) throws IOException, FormatException, ReadOnlyTagException, InsufficientSizeException, NdefFormatException {
        boolean writeSuccessfull = false;
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
                writeSuccessfull = true;
                nfcManagerTagWritten.OnSuccess();
            }

        }
        return writeSuccessfull;
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


    public NdefMessage createUrlNdefMessage(String uri) {
        NdefRecord record = createUriRecord(uri);
        return new NdefMessage(new NdefRecord[]{record});
    }

    public NdefRecord getFirstNdefRecord(NdefMessage ndefMessage) {
        NdefRecord ndefRecord = null;
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if (ndefRecords != null && ndefRecords.length > 0) {
            ndefRecord = ndefRecords[0];
        }

        return ndefRecord;
    }

    public boolean isNdefRecordOfTnfAndRdt(NdefRecord ndefRecord, short tnf, byte[] rdt) {
        return ndefRecord.getTnf() == tnf && Arrays.equals(ndefRecord.getType(), rdt);
    }

    public Pair<String, String> getTextFromNdefRecord(NdefRecord ndefRecord) {
        byte[] payload = ndefRecord.getPayload();
        //si contiene ndef tipo uri
        if (containsUriRecord(ndefRecord)) {
            //String payloadType = ndefRecord.toUri().toString().split()
            String uri = ndefRecord.toUri().toString();
            Log.d("PAYLOAD", "getTextFromNdefRecord: " + uri);
            //Si es tipo sms
            if (uri.startsWith("sms")) {
                return Pair.create("sms", buildSmsContent(ndefRecord));
                //Si es de tipo web
            } else if (uri.startsWith("http")) {
                Log.v("Url Http Record", "" + uri);
                return Pair.create("url", uri);
                //Otro tipo de Uri
            } else if (uri.startsWith("tel")) {
                Log.v("Phone Record", "" + uri);
                return Pair.create("phone", buildPhoneContent(ndefRecord));
                //Otro tipo de Uri
            } else if (uri.startsWith("vnd.android.nfc")) {
                Log.v("Aar Record", "" + getAarPackageName(ndefRecord));
                return Pair.create("App-Launcher", getAarPackageName(ndefRecord));
                //Otro tipo de Uri
            } else if (uri.startsWith("geo:")) {
                Log.v("Location record", "" + uri);
                return Pair.create("Geolocation", buildLocationRecord(ndefRecord));
                //Otro tipo de Uri
            } else if (uri.startsWith("mailto:")) {
                Log.v("Email record", "" + uri);
                return Pair.create("Email", buildEmailRecord(ndefRecord));
                //Otro tipo de Uri
            } else {
                Log.v("Uri Record", "" + uri);
                return Pair.create("uri", uri);
            }
            //Si es de tipo text
        } else if (isTextRecord(ndefRecord)) {
            return getTextContent(payload);
            //Otros Tipos
        } else {
            Log.v("TIPO DE TAG", "SIN TRATAR: " + String.valueOf(payload));
            byte[] byteContetn = payload;
            Object obj = bytesToObject(byteContetn);
            return Pair.create("other", String.valueOf(payload));

        }
    }

    Object bytesToObject(byte[] bytes) {
        Object o = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;

        try {
            in = new ObjectInputStream(bis);

            o = in.readObject();

            bis.close();
        } catch (Exception e) {
            Log.e("bytesToObject", "" + e.getMessage());
        }
        return o;
    }

    private String buildEmailRecord(NdefRecord ndefRecord) {
        String content = ndefRecord.toUri().toString();
        nfcManagerResponse.openEmailIntent(content);
        return ndefRecord.toUri().toString();
    }

    private String buildLocationRecord(NdefRecord ndefRecord) {
        String content = ndefRecord.toUri().toString();
        content.substring(3, content.length());
        String[] location = content.split(",");
        nfcManagerResponse.openLocationIntent(location[0], location[1]);
        return content;
    }


    private Pair<String, String> getTextContent(byte[] payload) {
        try {
            //Get the Text Encoding
            String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
            //Get the Language Code //Get the Text
            int languageCodeLength = payload[0] & 0077;
            String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
            text = "Text: " + text.concat("\nLang: " + languageCode);
            return Pair.create("text", text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new Pair<>("error", "Error reading tag");
        }
    }

    private String buildSmsContent(NdefRecord ndefRecord) {
        byte[] payload = ndefRecord.getPayload();
        String textEncoding = "UTF-8";
        String[] smsUriParts = ndefRecord.toUri().getSchemeSpecificPart().split("\\?body=");
        String tag = new String(payload, StandardCharsets.UTF_8).trim();
        String tag2 = tag.replaceAll("[^a-zA-Z0-9_-]", "");
        Log.v(" TAG", " Content: " + tag + " TAG2: " + tag2);
        String[] uriOcurrences = ndefRecord.toUri().toString().split("QT");
        String[] langTitle = tag2.split("QT");
        nfcManagerResponse.sendSms(smsUriParts[1], smsUriParts[0]);
        if ((langTitle.length - (uriOcurrences.length - 1)) > 1) {
            String lang = langTitle[langTitle.length - 1].substring(0, 2);
            String title = langTitle[langTitle.length - 1].substring(2);
            Log.v("SMS TITLE---------", "Title:" + title + "Number: " + smsUriParts[1] + " Body: " + smsUriParts[0] + "Lang: " + lang);
            return new String("Title: " + title + "\nNumber: " + smsUriParts[1] + "\nBody: " + smsUriParts[0] + "\nLang: " + lang);
        } else {
            Log.v("SMS-------------", "Number: " + smsUriParts[1] + " Body: " + smsUriParts[0]);
            return new String("Number: " + smsUriParts[1] + "\nBody: " + smsUriParts[0]);
        }
    }


    private String buildPhoneContent(NdefRecord ndefRecord) {
        String content = null;
        byte[] payload = ndefRecord.getPayload();
        try {
            String textEncoding = "UTF-8";
            int languageCodeLength = payload[0] & 0077;

            String phoneNumber = ndefRecord.toUri().toString();
            String tittle = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);

            if (tittle.startsWith("tel:")) {
                content = phoneNumber;
            } else {
                content = tittle.substring(3, tittle.length());
                content = phoneNumber + " " + content;

            }
            nfcManagerResponse.sendPhoneCall(ndefRecord);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return content;
    }

    private String getAarPackageName(NdefRecord ndefRecord) {
        String content = null;
        byte[] payload = ndefRecord.getPayload();
        try {
            String textEncoding = "UTF-8";
            String phoneNumber = ndefRecord.toUri().toString();
            content = new String(payload, 0, payload.length, textEncoding);

            nfcManagerResponse.sendAarLauncher(content);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }

    private boolean containsUriRecord(NdefRecord ndefRecord) {
        if (ndefRecord.toUri() != null) {
            return true;
        } else {
            return false;
        }

    }

    public NdefMessage createTextNdefMessage(String text) {
        NdefRecord record = createTextRecord(text);
        return new NdefMessage(new NdefRecord[]{record});
    }

    public boolean isTextRecord(NdefRecord record) {

        if (record == null) {
            return false;
        }

        return isNdefRecordOfTnfAndRdt(record, NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT);
    }

    public boolean isUriRecord(NdefRecord record) {

        if (record == null) {
            return false;
        }

        return isNdefRecordOfTnfAndRdt(record, NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_URI);
    }

    public boolean isMimeRecord(NdefRecord record, String mimeType) {

        if (record == null || mimeType == null) {
            return false;
        }

        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));

        return isNdefRecordOfTnfAndRdt(record, NdefRecord.TNF_MIME_MEDIA, mimeBytes);
    }

    public boolean isExternalTypeRecord(NdefRecord record, String domain, String type) {

        if (record == null || domain == null || type == null) {
            return false;
        }

        byte[] externalTypeBytes = String.format("%s:%s", domain, type).getBytes(Charset.forName("US-ASCII"));

        return isNdefRecordOfTnfAndRdt(record, NdefRecord.TNF_EXTERNAL_TYPE, externalTypeBytes);
    }

    public NdefMessage createExternalTypeNdefMessage(String type, byte[] data) {

        NdefRecord externalRecord = NdefRecord.createExternal("com.packtpub", type, data);

        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{externalRecord});

        return ndefMessage;
    }

    public NdefMessage createAarRecord(Intent intent, String packageName) {
        NdefRecord aaRecord = NdefRecord.createApplicationRecord(packageName);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{aaRecord});
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        return ndefMessage;
    }

    public void makeTagReadonly(Intent intent) {

        Tag tag = getTagFromIntent(intent);

        try {

            if (tag != null) {

                Ndef ndef = Ndef.get(tag);

                if (ndef != null) {
                    ndef.connect();

                    if (ndef.canMakeReadOnly()) {
                        ndef.makeReadOnly();
                    }

                    ndef.close();
                }
            }

        } catch (Exception e) {
            Log.e("makeTagReadonly", "" + e.getMessage());
        }

    }

    public boolean isForegroundEnabled() {

        return true;
    }

    public void formatEmptyTag(Intent intent, String type) {
        NdefRecord ndefEmptyRecord = new NdefRecord(NdefRecord.TNF_EMPTY, new byte[]{}, new byte[]{}, new byte[]{});
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ndefEmptyRecord});
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        try {
            if (writeNdefMessage(tag, ndefMessage)) {
                nfcManagerTagWritten.OnSuccess();
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (ReadOnlyTagException e) {
            e.printStackTrace();
        } catch (InsufficientSizeException e) {
            e.printStackTrace();
        } catch (NdefFormatException e) {
            e.printStackTrace();
        }
    }

    public NdefMessage writeNdefWifiMessage(Intent intent, WifiNetwork wifiTag) {
        byte[] payload = generateNdefPayload(wifiTag);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                NfcUtilsOld.NFC_TOKEN_MIME_TYPE.getBytes(Charset.forName("US-ASCII")),
                new byte[0],
                payload);
        NdefRecord aarRecord = NdefRecord.createApplicationRecord(PACKAGE_NAME);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{mimeRecord, aarRecord});

        try {
            if (writeNdefMessage(tag, ndefMessage)) {
                nfcManagerTagWritten.OnSuccess();
            } else {
                nfcManagerTagWritten.OnError("Error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (ReadOnlyTagException e) {
            e.printStackTrace();
        } catch (InsufficientSizeException e) {
            e.printStackTrace();
        } catch (NdefFormatException e) {
            e.printStackTrace();
        }


        return ndefMessage;
    }


    private static byte[] generateNdefPayload(WifiNetwork wifiNetwork) {
        String ssid = wifiNetwork.getSsid();
        short ssidSize = (short) ssid.getBytes().length;

        short authType;
        switch (wifiNetwork.getSecurity()) {
            case WPA_PSK:
                authType = AUTH_TYPE_WPA_PSK;
                break;
            case WPA2_PSK:
                authType = AUTH_TYPE_WPA2_PSK;
                break;
            case WPA_EAP:
                authType = AUTH_TYPE_WPA_EAP;
                break;
            case WPA2_EAP:
                authType = AUTH_TYPE_WPA2_EAP;
                break;
            default:
                authType = AUTH_TYPE_OPEN;
                break;
        }

        /*short encType;
        switch (wifiNetwork.getEncType()) {
            case WEP:
                encType = ENC_TYPE_WEP;
                break;
            case TKIP:
                encType = ENC_TYPE_TKIP;
                break;
            case AES:
                encType = ENC_TYPE_AES;
                break;
            case AES_TKIP:
                encType = ENC_TYPE_AES_TKIP;
                break;
            default:
                encType = ENC_TYPE_NONE;
                break;
        }*/

        String networkKey = wifiNetwork.getPassword();
        short networkKeySize = (short) networkKey.getBytes().length;

        byte[] macAddress = new byte[MAX_MAC_ADDRESS_SIZE_BYTES];
        for (int i = 0; i < MAX_MAC_ADDRESS_SIZE_BYTES; i++) {
            macAddress[i] = (byte) 0xff;
        }

        /* Fill buffer */

        int bufferSize = 18 + ssidSize + networkKeySize; // size of required credential attributes

        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        buffer.putShort(CREDENTIAL_FIELD_ID);
        buffer.putShort((short) (bufferSize - 4));

//        buffer.putShort(NETWORK_INDEX_FIELD_ID);
//        buffer.putShort((short) 1);
//        buffer.put(NETWORK_INDEX_DEFAULT_VALUE);

        buffer.putShort(SSID_FIELD_ID);
        buffer.putShort(ssidSize);
        buffer.put(ssid.getBytes());

        buffer.putShort(AUTH_TYPE_FIELD_ID);
        buffer.putShort((short) 2);
        buffer.putShort(authType);

//        buffer.putShort(ENC_TYPE_FIELD_ID);
//        buffer.putShort((short) 2);
//        buffer.putShort(ENC_TYPE_NONE); // FIXME

        buffer.putShort(NETWORK_KEY_FIELD_ID);
        buffer.putShort(networkKeySize);
        buffer.put(networkKey.getBytes());

//        buffer.putShort(MAC_ADDRESS_FIELD_ID);
//        buffer.putShort((short) MAX_MAC_ADDRESS_SIZE_BYTES);
//        buffer.put(macAddress);

        return buffer.array();
    }

    public interface NfcManagerResponse {

        void sendSms(String phoneNumber, String message);

        void sendPhoneCall(NdefRecord content);

        void sendAarLauncher(String packageName);

        void openLocationIntent(String latitude, String longitude);

        void openEmailIntent(String content);
    }

    public interface NfcManagerTagWritten {
        void OnSuccess();

        void OnError(String message);
    }
}