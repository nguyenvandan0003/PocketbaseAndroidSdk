package com.dannv.pocketbasesdk.storge

import com.danphuong.utils.storge.PreferenceUtils

object PocketBaseSharePrefUtils {

	var pref = PreferenceUtils.instance

	val token_KEY = "TOKEN"
	var token : String?
		set(value) {
			pref!!.put(token_KEY, value)
		}
		get() {
			return pref?.get(token_KEY, String::class.java)
		}
}