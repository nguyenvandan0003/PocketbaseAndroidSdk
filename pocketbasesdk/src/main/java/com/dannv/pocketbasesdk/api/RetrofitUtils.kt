package com.dannv.pocketbasesdk.api

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

	fun <T> T.createRequestBody(): okhttp3.RequestBody {
		val json = Gson().toJson(this)
		return json.toRequestBody("application/json".toMediaTypeOrNull())
	}