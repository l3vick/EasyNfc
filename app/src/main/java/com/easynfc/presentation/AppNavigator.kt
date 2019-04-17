package com.easynfc.presentation

import com.easynfc.MainActivity
import com.easynfc.navigation.NavigationExecutor
import com.easynfc.presentation.ui.menu.MenuActivity
import com.easynfc.presentation.ui.read.ReadActivity

class AppNavigator (private val navigationExecutor: NavigationExecutor) {


    fun startMainActivity() {
        navigationExecutor.request { MainActivity.start(it) }
    }

    fun startReadActivity() {
        navigationExecutor.request { ReadActivity.start(it) }
    }

    fun startMenuActivity() {
        navigationExecutor.request { MenuActivity.start(it) }
    }


}
