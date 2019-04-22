package com.easynfc.presentation

import com.easynfc.MainActivity
import com.easynfc.navigation.NavigationExecutor

class AppNavigator (private val navigationExecutor: NavigationExecutor) {


    fun startMainActivity() {
        navigationExecutor.request { MainActivity.start(it) }
    }

    fun startReadActivity() {
        //navigationExecutor.request { ReadActivity.start(it) }
    }

    fun startMenuActivity() {
        //navigationExecutor.request { MenuActivity.start(it) }
    }


}
