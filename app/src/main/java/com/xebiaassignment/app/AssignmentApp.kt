package com.xebiaassignment.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AssignmentApp : Application() {

    companion object {
        var appLanguage: String = "en-US"
    }

}