package com.vipera.onepay.util

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.view.WindowManager
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AppUtils {


    companion object {

        fun fullScreenLayout(activity: Activity) {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            val decorView = activity.window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView.systemUiVisibility = uiOptions
        }

        fun translucidNavigationBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val w = activity.window
                w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }
        }

        fun hideNavigationBar(activity: Activity) {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        fun hideStatusBar(activity: Activity) {
            val decorView = activity.window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
            decorView.systemUiVisibility = uiOptions
        }


        fun showMessage(context: Context, message: String, title: String? = null) {
            val builder = AlertDialog.Builder(context as Activity)

            // Set the alert dialog title
            builder.setTitle(title)

            // Display a message on alert dialog
            builder.setMessage(message)

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Aceptar") { dialog, which ->
            }

            // Display a negative button on alert dialog
            builder.setNegativeButton("Cancelar") { dialog, which ->

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        fun getCurDate(): String {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formatted = current.format(formatter)
            return formatted
        }


    }

}