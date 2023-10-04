package com.dannv.pocketbasesdk.impl

import android.text.TextUtils
import android.util.Log
import com.dannv.pocketbasesdk.api.PocketBaseServices
import com.dannv.pocketbasesdk.api.PocketbaseError
import com.dannv.pocketbasesdk.api.PocketbaseErrorException
import com.dannv.pocketbasesdk.api.createRequestBody
import com.dannv.pocketbasesdk.storge.PocketBaseSharePrefUtils
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

class PocketbaseImpl(var services: PocketBaseServices) {

	suspend fun <P, T> login(param: P, clazz: Class<T>): T {
		var response = ""
		try {
			response =  services.login(param.createRequestBody())
			return Gson().fromJson(response, clazz)
		} catch (e: UnknownHostException) {
			throw e
		} catch (e: HttpException) {
			try {
				var error = ((e as HttpException).response()?.errorBody() as ResponseBody).string()
				throw PocketbaseErrorException(Gson().fromJson(error, PocketbaseError::class.java))
			} catch (e: Exception) {
				throw e
			}
		} catch (e: Exception) {
			throw e
		}
	}

	suspend fun <P> resetPassword(param: P): Boolean {
		var response: String? = null
		try {
			response =  services.resetPassword(param.createRequestBody())
			return !response.isNullOrBlank() || TextUtils.equals(response.toString(), "null")
		} catch (e: UnknownHostException) {
			return false
		} catch (e: HttpException) {
			return false

		} catch (e: Exception) {
			return false
		}
	}

	suspend fun <T> fetchList(collecttion: String,
									  page: Int = 1,
									  perpage: Int = 30,
									  sort: String = "-created",
									  clazz: Class<T>): T {
		var response = ""
		try {
			response = services.fetchList(collecttion, page, perpage, sort)
			return Gson().fromJson(response, clazz)
		} catch (e: UnknownHostException) {
			throw e
		}  catch (e: HttpException) {
			try {
				var error = ((e as HttpException).response()?.errorBody() as ResponseBody).string()
				throw PocketbaseErrorException(Gson().fromJson(error, PocketbaseError::class.java))
			} catch (e: Exception) {
				throw e
			}
		} catch (e: Exception) {
			throw e
		}
	}

	suspend fun <T> getDetail(collecttion: String,
									  id: String,
									  clazz: Class<T>): T {
		var response = ""
		try {
			response = services.fetchDetail(collecttion, id)
			return Gson().fromJson(response, clazz)
		} catch (e: UnknownHostException) {
			throw e
		}  catch (e: HttpException) {
			try {
				var error = ((e as HttpException).response()?.errorBody() as ResponseBody).string()
				throw PocketbaseErrorException(Gson().fromJson(error, PocketbaseError::class.java))
			} catch (e: Exception) {
				throw e
			}
		} catch (e: Exception) {
			throw e
		}
	}

	suspend fun <T> search(collecttion: String,
									  page: Int = 1,
									  perpage: Int = 30,
									  sort: String = "?sort=-created",
						              filter: String,
									  clazz: Class<T>): T {
		var response = ""
		try {
			response = services.search(collecttion, page, perpage, sort, filter)
			return Gson().fromJson(response, clazz)
		} catch (e: UnknownHostException) {
			throw e
		}  catch (e: HttpException) {
			try {
				var error = ((e as HttpException).response()?.errorBody() as ResponseBody).string()
				throw PocketbaseErrorException(Gson().fromJson(error, PocketbaseError::class.java))
			} catch (e: Exception) {
				throw e
			}
		} catch (e: Exception) {
			throw e
		}
	}

	suspend fun <P, T> update(collecttion: String,
									  id: String,
									  param: P, clazz: Class<T>): T {
	var response = ""
		try {
			response = services.update(collecttion,
												id,
												param.createRequestBody())
			return Gson().fromJson(response, clazz)
		} catch (e: UnknownHostException) {
			throw e
		}  catch (e: HttpException) {
			try {
				var error = ((e as HttpException).response()?.errorBody() as ResponseBody).string()
				throw PocketbaseErrorException(Gson().fromJson(error, PocketbaseError::class.java))
			} catch (e: Exception) {
				throw e
			}
		} catch (e: Exception) {
			throw e
		}
	}

	suspend fun <P, T> create(collecttion: String,
									  param: P, clazz: Class<T>): T {
		var response = ""
		try {
			response = services.create(collecttion,
													 param.createRequestBody())
			return Gson().fromJson(response, clazz)
		} catch (e: UnknownHostException) {
			throw e
		}  catch (e: HttpException) {
			try {
				var error = ((e as HttpException).response()?.errorBody() as ResponseBody).string()
				throw PocketbaseErrorException(Gson().fromJson(error, PocketbaseError::class.java))
			} catch (e: Exception) {
				throw e
			}
		} catch (e: Exception) {
			throw e
		}
	}
	suspend fun <P, T> delete(collecttion: String,
									  id: String, clazz: Class<T>): T {
		var response = ""
		try {
			response = services.delete(collecttion,
												id)
			return Gson().fromJson(response, clazz)
		} catch (e: UnknownHostException) {
			throw e
		} catch (e: HttpException) {
			try {
				var error = ((e as HttpException).response()?.errorBody() as ResponseBody).string()
				throw PocketbaseErrorException(Gson().fromJson(error, PocketbaseError::class.java))
			} catch (e: Exception) {
				throw e
			}
		} catch (e: Exception) {
			throw e
		}
	}
}