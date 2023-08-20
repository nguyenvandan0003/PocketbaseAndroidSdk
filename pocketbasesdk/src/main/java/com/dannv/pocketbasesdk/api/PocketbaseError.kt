package com.dannv.pocketbasesdk.api

data class PocketbaseError(
	val code: Int,
	val message: String,
	val data: Map<String, String>
)

class PocketbaseErrorException(val error: PocketbaseError) : Exception(error.message)
