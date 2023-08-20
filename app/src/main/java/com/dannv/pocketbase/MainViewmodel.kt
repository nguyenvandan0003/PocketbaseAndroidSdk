package com.dannv.pocketbase

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dannv.pocketbase.model.BaseResponse
import com.dannv.pocketbase.model.Hihi
import com.dannv.pocketbase.model.LoginResponse
import com.dannv.pocketbase.model.Param
import com.dannv.pocketbasesdk.di.RetrofitDi
import com.dannv.pocketbasesdk.impl.PocketbaseImpl
import com.dannv.pocketbasesdk.storge.PocketBaseSharePrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(val pocketbaseImpl : PocketbaseImpl): ViewModel() {

	var responseLoginData: LoginResponse? = null
	var apiStatus = MutableStateFlow<Boolean>(false)

	fun login(email: String, password: String) {
		apiStatus.value = true
		viewModelScope.launch (Dispatchers.IO) {
			try {
				var login = pocketbaseImpl.login(Param(email, password), LoginResponse::class.java)
				PocketBaseSharePrefUtils.token = "login.token"
				RetrofitDi.setToken()
				pocketbaseImpl.create("HiHi", Hihi("dannv", "12"), BaseResponse::class.java)
				apiStatus.value = false
			} catch (e: Exception) {
				Log.d("dannv", e.toString())
				apiStatus.value = false
			}
		}
	}
}