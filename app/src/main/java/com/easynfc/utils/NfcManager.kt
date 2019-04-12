package com.easynfc.utils
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.tech.NdefFormatable
import android.nfc.tech.Ndef
import android.content.IntentFilter
import android.content.pm.PackageManager
import com.easynfc.model.TagData
import android.nfc.NdefRecord
import android.nfc.NdefMessage
import android.nfc.Tag
import android.os.Parcelable
import java.util.*


class NfcManager {

    private var nfcAdapter : NfcAdapter?
    private var context: Activity?

    constructor(context: Activity){
        // Check if NFC is supported and enabled
        this.context = context
        nfcAdapter = NfcAdapter.getDefaultAdapter(context)
    }

    fun isNfcEnabledDevice(): Boolean {
        val hasFeature = context?.getPackageManager()?.hasSystemFeature(PackageManager.FEATURE_NFC)
        val isEnabled = nfcAdapter != null && nfcAdapter!!.isEnabled
        return hasFeature!! && isEnabled
    }


    fun enableForegroundDispatch() {
        if (isNfcEnabledDevice()) {
            val intent = Intent(context, context?.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            val intentFilter = arrayOf<IntentFilter>()
            val techList = arrayOf(arrayOf(Ndef::class.java.name), arrayOf(NdefFormatable::class.java.name))
            nfcAdapter?.enableForegroundDispatch(context, pendingIntent, intentFilter, techList)
        }

    }

    fun disableForegroundDispatch(){
        nfcAdapter?.disableForegroundDispatch(context)
    }


    fun proccessIntent(intent: Intent): TagData? {
        val tag = getTagFromIntent(intent)
        val ndefMessage = getNdefMessageFromIntent(intent)
        if (ndefMessage != null) {
            val ndefRecord = getFirstNdefRecord(ndefMessage)
            if (ndefRecord != null) {
                val message = String(ndefRecord!!.getPayload())
                val type = getType(ndefRecord)
                return TagData(type, message, tag.getTechList(), tnfToString(ndefRecord!!.getTnf()), rtdToString(ndefRecord!!.getType()), Integer.toString(ndefRecord!!.getPayload().size))
            }
        }
        return null
    }

    fun getTagFromIntent(intent: Intent): Tag {
        return intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag
    }


    private fun getNdefMessageFromIntent(intent: Intent): NdefMessage? {

        var ndefMessage: NdefMessage? = null

        val extra = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)

        if (extra != null && extra.size > 0) {
            ndefMessage = extra[0] as NdefMessage
        }
        return ndefMessage
    }

    private fun getFirstNdefRecord(ndefMessage: NdefMessage): NdefRecord? {
        var ndefRecord: NdefRecord? = null
        val ndefRecords = ndefMessage.records

        if (ndefRecords != null && ndefRecords.size > 0) {
            ndefRecord = ndefRecords[0]
        }

        return ndefRecord
    }

    private fun getType(ndefRecord: NdefRecord): String {
        if (ndefRecord.toUri() != null) {
            val uri = ndefRecord.toUri().toString()
            return if (uri.startsWith("sms")) {
                "sms"
                //Si es de tipo web
            } else if (uri.startsWith("http")) {
                "url"
            } else if (uri.startsWith("tel")) {
                "phone"
            } else if (uri.startsWith("vnd.android.nfc")) {
                "aar"
            } else if (uri.startsWith("geo:")) {
                "location"
            } else if (uri.startsWith("mailto:")) {
                "email"
            } else {
                "uri"
            }
        } else if (isTextRecord(ndefRecord)) {
            return "text"
        } else {
            val message = String(ndefRecord.payload)
            return if (message.contains("E") && message.contains("'")) {
                "wifi"
            } else {
                "other"
            }
        }
    }

    private fun isTextRecord(record: NdefRecord?): Boolean {

        return if (record == null) {
            false
        } else isNdefRecordOfTnfAndRdt(record, NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT)

    }

    private fun isNdefRecordOfTnfAndRdt(ndefRecord: NdefRecord, tnf: Short, rdt: ByteArray): Boolean {
        return ndefRecord.tnf == tnf && Arrays.equals(ndefRecord.type, rdt)
    }

    private fun rtdToString(rdt: ByteArray): String {
        return if (Arrays.equals(rdt, NdefRecord.RTD_ALTERNATIVE_CARRIER)) {
            "RTD_ALTERNATIVE_CARRIER"
        } else if (Arrays.equals(rdt, NdefRecord.RTD_HANDOVER_CARRIER)) {
            "RTD_HANDOVER_CARRIER"
        } else if (Arrays.equals(rdt, NdefRecord.RTD_HANDOVER_REQUEST)) {
            "RTD_HANDOVER_REQUEST"
        } else if (Arrays.equals(rdt, NdefRecord.RTD_HANDOVER_SELECT)) {
            "RTD_HANDOVER_SELECT"
        } else if (Arrays.equals(rdt, NdefRecord.RTD_SMART_POSTER)) {
            "RTD_SMART_POSTER"
        } else if (Arrays.equals(rdt, NdefRecord.RTD_TEXT)) {
            "RTD_TEXT"
        } else if (Arrays.equals(rdt, NdefRecord.RTD_URI)) {
            "RTD_URI"
        } else {
            "RTD_UNKNOWN"
        }
    }

    private fun tnfToString(tnf: Short): String {
        return when (tnf) {
            NdefRecord.TNF_EMPTY ->  "TNF_EMPTY"
            NdefRecord.TNF_ABSOLUTE_URI ->  "TNF_ABSOLUTE_URI"
            NdefRecord.TNF_EXTERNAL_TYPE ->  "TNF_EXTERNAL_TYPE"
            NdefRecord.TNF_MIME_MEDIA ->  "TNF_MIME_MEDIA"
            NdefRecord.TNF_UNCHANGED ->  "TNF_UNCHANGED"
            NdefRecord.TNF_WELL_KNOWN ->  "TNF_WELL_KNOWN"
            NdefRecord.TNF_UNKNOWN ->  "TNF_UNKNOWN"
            else ->  "TNF_UNKNOWN"
        }
    }
}