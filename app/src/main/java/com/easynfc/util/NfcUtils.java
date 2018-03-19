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

import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.data.model.TagResponse;
import com.easynfc.data.model.Wifi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;


/**
 * Created by pablorojas on 26/2/18.
 */

public class NfcUtils {

    private Activity activity;
    private NfcAdapter nfcAdapter;
    private static NfcUtils INSTANCE = null;

    public static final short AUTH_TYPE_OPEN = 0x0001;
    public static final short AUTH_TYPE_WPA_PSK = 0x0002;
    public static final short AUTH_TYPE_WPA_EAP = 0x0008;
    public static final short AUTH_TYPE_WPA2_EAP = 0x0010;
    public static final short AUTH_TYPE_WPA2_PSK = 0x0020;
    public static final int MAX_MAC_ADDRESS_SIZE_BYTES = 6;
    public static final String NFC_TOKEN_MIME_TYPE = "application/vnd.wfa.wsc";
    public static final short CREDENTIAL_FIELD_ID = 0x100e;
    public static final short NETWORK_KEY_FIELD_ID = 0x1027;
    public static final short SSID_FIELD_ID = 0x1045;
    public static final short AUTH_TYPE_FIELD_ID = 0x1003;

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


    public void handleIntent(Intent intent, TagReadedCallback callback) {
        TagResponse tagResponse;
        Tag tag = getTagFromIntent(intent);
        NdefMessage ndefMessage = getNdefMessageFromIntent(intent);
        if (ndefMessage != null) {
            NdefRecord ndefRecord = getFirstNdefRecord(ndefMessage);
            if (ndefRecord != null) {
                String message = new String(ndefRecord.getPayload());
                String type = getType(ndefRecord);
                tagResponse = new TagResponse(type, message, tag.getTechList(), tnfToString(ndefRecord.getTnf()), rtdToString(ndefRecord.getType()), Integer.toString(ndefRecord.getPayload().length));
                callback.OnSuccess(tagResponse);
            }
        }
    }

    private String getType(NdefRecord ndefRecord) {
        if (ndefRecord.toUri() != null) {
            String uri = ndefRecord.toUri().toString();
            if (uri.startsWith("sms")) {
                return "sms";
                //Si es de tipo web
            } else if (uri.startsWith("http")) {
              return "url";
            } else if (uri.startsWith("tel")) {
                return "phone";
            } else if (uri.startsWith("vnd.android.nfc")) {
                return "aar";
            } else if (uri.startsWith("geo:")) {
                return "location";
            } else if (uri.startsWith("mailto:")) {
                return "email";
            }else {
                return "uri";
            }
        } else if (isTextRecord(ndefRecord)) {
            return "text";
        } else {
            String message = new String(ndefRecord.getPayload());
            if (message.contains("E") && message.contains("'")){
                return "wifi";
            }else{
                return "other";
            }
        }
    }
    private boolean isTextRecord(NdefRecord record) {

        if (record == null) {
            return false;
        }

        return isNdefRecordOfTnfAndRdt(record, NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT);
    }

    private boolean isNdefRecordOfTnfAndRdt(NdefRecord ndefRecord, short tnf, byte[] rdt) {
        return ndefRecord.getTnf() == tnf && Arrays.equals(ndefRecord.getType(), rdt);
    }


    private NdefMessage getNdefMessageFromIntent(Intent intent) {

        NdefMessage ndefMessage = null;

        Parcelable[] extra = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (extra != null && extra.length > 0) {
            ndefMessage = (NdefMessage) extra[0];
        }
        return ndefMessage;
    }

    private NdefRecord getFirstNdefRecord(NdefMessage ndefMessage) {
        NdefRecord ndefRecord = null;
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if (ndefRecords != null && ndefRecords.length > 0) {
            ndefRecord = ndefRecords[0];
        }

        return ndefRecord;
    }



    public void writeSimpleTextTag(Intent intent, String text, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        NdefRecord uriRecord = createTextRecord(text);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        writeNdefMessage(intent, ndefMsg, callback);
    }

    public void writeUrlTag(Intent intent, String text, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        NdefRecord uriRecord = createUriRecord(text);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        writeNdefMessage(intent, ndefMsg, callback);
    }

    public void writeSmsTag(Intent intent, String number, String text, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        String smsUri = String.format("sms:%s?body=%s", number, URLEncoder.encode(text));
        NdefRecord uriRecord = createUriRecord(smsUri);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        writeNdefMessage(intent, ndefMsg, callback);
    }

    public void writePhoneTag(Intent intent, String number, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        NdefRecord uriRecord = createUriRecord("tel:" + number);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        writeNdefMessage(intent, ndefMsg, callback);
    }

    public void writeAppLauncherTag(Intent intent, String aar, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        NdefRecord uriRecord = NdefRecord.createApplicationRecord(aar);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        writeNdefMessage(intent, ndefMsg, callback);
    }

    public void writeLocationTag(Intent intent, String latitude, String longitude, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        NdefRecord uriRecord = createUriRecord("geo:" + latitude + "," + longitude);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        writeNdefMessage(intent, ndefMsg, callback);
    }


    public void writeWifiTag(Intent intent, Wifi wifi, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        NdefMessage ndefMsg = writeNdefWifiMessage(wifi);
        writeNdefMessage(intent, ndefMsg, callback);
    }

    public void writeEmailTag(Intent intent, String email, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        NdefRecord uriRecord = createUriRecord("mailto:" + email);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        writeNdefMessage(intent, ndefMsg, callback);
    }

    public void formatEmptyTag(Intent intent, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        NdefRecord ndefEmptyRecord = new NdefRecord(NdefRecord.TNF_EMPTY, new byte[]{}, new byte[]{}, new byte[]{});
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ndefEmptyRecord});
        writeNdefMessage(intent, ndefMessage, callback);
    }

    public void lockTag(Intent intent, TagWrittenCallback callback) {
        if (isNfcIntent(intent)) {
            makeTagReadonly(intent);
        }
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

    public NdefMessage writeNdefWifiMessage(Wifi wifi) {
        byte[] payload = generateNdefPayload(wifi);
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                NFC_TOKEN_MIME_TYPE.getBytes(Charset.forName("US-ASCII")),
                new byte[0],
                payload);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[] {mimeRecord});
        return ndefMessage;
    }

    private static byte[] generateNdefPayload(Wifi wifiNetwork) {
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

    private String rtdToString(byte[] rdt) {
        if (Arrays.equals(rdt, NdefRecord.RTD_ALTERNATIVE_CARRIER)) {
            return "RTD_ALTERNATIVE_CARRIER";
        } else if (Arrays.equals(rdt, NdefRecord.RTD_HANDOVER_CARRIER)) {
            return "RTD_HANDOVER_CARRIER";
        } else if (Arrays.equals(rdt, NdefRecord.RTD_HANDOVER_REQUEST)) {
            return "RTD_HANDOVER_REQUEST";
        } else if (Arrays.equals(rdt, NdefRecord.RTD_HANDOVER_SELECT)) {
            return "RTD_HANDOVER_SELECT";
        } else if (Arrays.equals(rdt, NdefRecord.RTD_SMART_POSTER)) {
            return "RTD_SMART_POSTER";
        } else if (Arrays.equals(rdt, NdefRecord.RTD_TEXT)) {
            return "RTD_TEXT";
        } else if (Arrays.equals(rdt, NdefRecord.RTD_URI)) {
            return "RTD_URI";
        } else {
            return "RTD_UNKNOWN";
        }
    }

    private String tnfToString(short tnf) {
        switch (tnf) {
            case NdefRecord.TNF_EMPTY:
                return "TNF_EMPTY";
            case NdefRecord.TNF_ABSOLUTE_URI:
                return "TNF_ABSOLUTE_URI";
            case NdefRecord.TNF_EXTERNAL_TYPE:
                return "TNF_EXTERNAL_TYPE";
            case NdefRecord.TNF_MIME_MEDIA:
                return "TNF_MIME_MEDIA";
            case NdefRecord.TNF_UNCHANGED:
                return "TNF_UNCHANGED";
            case NdefRecord.TNF_WELL_KNOWN:
                return "TNF_WELL_KNOWN";
            default:
            case NdefRecord.TNF_UNKNOWN:
                return "TNF_UNKNOWN";
        }
    }

    public void emulateTextTag(String text) {
        NdefRecord uriRecord = createTextRecord(text);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        nfcAdapter.setNdefPushMessage(ndefMsg, activity);
    }

    public void emulateAarTag(String aar) {
        NdefRecord uriRecord = NdefRecord.createApplicationRecord(aar);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        nfcAdapter.setNdefPushMessage(ndefMsg, activity);
    }

    public void emulateEmailTag(String email) {
        NdefRecord uriRecord = createUriRecord("mailto:" + email);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        nfcAdapter.setNdefPushMessage(ndefMsg, activity);
    }

    public void emulateLocationTag(String latitude, String longitude) {
        NdefRecord uriRecord = createUriRecord("geo:" + latitude + "," + longitude);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        nfcAdapter.setNdefPushMessage(ndefMsg, activity);
    }

    public void emulatePhoneTag(String phone) {
        NdefRecord uriRecord = createUriRecord("tel:" + phone);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        nfcAdapter.setNdefPushMessage(ndefMsg, activity);
    }

    public void emulateSmsTag(String number, String text) {
        String smsUri = String.format("sms:%s?body=%s", number, URLEncoder.encode(text));
        NdefRecord uriRecord = createUriRecord(smsUri);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        nfcAdapter.setNdefPushMessage(ndefMsg, activity);
    }

    public void emulateUrlTag(String url) {
        NdefRecord uriRecord = createUriRecord(url);
        NdefMessage ndefMsg = new NdefMessage(new NdefRecord[]{uriRecord});
        nfcAdapter.setNdefPushMessage(ndefMsg, activity);
    }

    public void emulateWifiTag(Wifi wifi) {
        NdefMessage ndefMsg = writeNdefWifiMessage(wifi);
        nfcAdapter.setNdefPushMessage(ndefMsg, activity);
    }

    public interface TagReadedCallback{
        void OnSuccess(TagResponse tagResponse);
    }

    public interface TagWrittenCallback {
        void OnSuccess();

        void OnError();
    }

}
