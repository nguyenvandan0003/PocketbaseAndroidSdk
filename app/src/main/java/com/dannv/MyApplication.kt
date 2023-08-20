package com.dannv

import android.app.Application
import com.danphuong.utils.storge.PreferenceUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
	override fun onCreate() {
		super.onCreate()
		PreferenceUtils.initPreference(this, "Pocketbase")
	}
}