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
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.easynfc.R;
import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.data.model.TagData;
import com.easynfc.data.model.WifiTag;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;


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
        TagData tagData;
        Tag tag = getTagFromIntent(intent);
        NdefMessage ndefMessage = getNdefMessageFromIntent(intent);
        if (ndefMessage != null) {
            NdefRecord ndefRecord = getFirstNdefRecord(ndefMessage);
            if (ndefRecord != null) {
                Pair<String, String> tipeContent = getTextFromNdefRecord(ndefRecord);
                tagData = new TagData(tipeContent.first, tipeContent.second, TextUtils.join(", ", tag.getTechList()), tnfToString(ndefRecord.getTnf()) + ", " + rtdToString(ndefRecord.getType()),tipeContent.first, Integer.toString(ndefRecord.getPayload().length));
                callback.OnSuccess(tagData);
            }
        }
    }


    //TODO Refactor methos to read tags

    private Pair<String, String> getTextFromNdefRecord(NdefRecord ndefRecord) {
        byte[] payload = ndefRecord.getPayload();
        //si contiene ndef tipo uri
        if (ndefRecord.toUri() != null) {
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
            }else {
                Log.v("Uri Record", "" + uri);
                return Pair.create("uri", uri);
            }
            //Si es de tipo text
        } else if (isTextRecord(ndefRecord)) {
            return getTextContent(payload);
            //Otros Tipos
        } else {
            Log.v("TIPO DE TAG", "SIN TRATAR: " + String.valueOf(payload));
            byte [] byteContetn = payload;
            Object obj = bytesToObject(byteContetn);
            return Pair.create("other", String.valueOf(payload));

        }
    }

    private Object bytesToObject(byte[] bytes) {
        Object o = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;

        try {
            in = new ObjectInputStream(bis);

            o = in.readObject();

            bis.close();
        } catch (Exception e) {
            Log.e("bytesToObject",""+ e.getMessage());
        }
        return o;
    }

    private String buildEmailRecord(NdefRecord ndefRecord) {
        String content  = ndefRecord.toUri().toString();
        return ndefRecord.toUri().toString();
    }

    private String buildLocationRecord(NdefRecord ndefRecord) {
        String content  = ndefRecord.toUri().toString();
        content.substring(3,content.length());
        String [] location = content.split(",");
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

            if(tittle.startsWith("tel:")){
                content = phoneNumber;
            }else{
                content = tittle.substring(3,tittle.length());
                content = phoneNumber + " " + content;

            }

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

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
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
    //TODO END OF READER


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


    public void writeWifiTag(Intent intent, WifiTag wifiTag, TagWrittenCallback callback) throws ReadOnlyTagException, NdefFormatException, FormatException, InsufficientSizeException, IOException {
        NdefMessage ndefMessage = writeNdefWifiMessage(wifiTag);
        writeNdefMessage(intent, ndefMessage, callback);
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

    public NdefMessage writeNdefWifiMessage(WifiTag wifiTag) {
        byte[] payload = generateNdefPayload(wifiTag);
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                NFC_TOKEN_MIME_TYPE.getBytes(Charset.forName("US-ASCII")),
                new byte[0],
                payload);
        NdefRecord aarRecord = NdefRecord.createApplicationRecord(PACKAGE_NAME);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[] {mimeRecord, aarRecord});
        return ndefMessage;
    }

    private static byte[] generateNdefPayload(WifiTag wifiNetwork) {
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

    public interface TagReadedCallback{
        void OnSuccess(TagData tagData);
    }

    public interface TagWrittenCallback {
        void OnSuccess();

        void OnError();
    }

}
