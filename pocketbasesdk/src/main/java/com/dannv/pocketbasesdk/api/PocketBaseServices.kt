package com.dannv.pocketbasesdk.api

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PocketBaseServices {

	@POST("/api/collections/users/auth-with-password")
	suspend fun login(@Body requestBody: RequestBody): String

	@POST("/api/collections/users/auth-refresh")
	suspend fun refreshToken(): String

	@POST("/api/collections/users/request-password-reset")
	suspend fun resetPassword(@Body requestBody: RequestBody): String

	@POST("/api/collections/users/confirm-password-reset")
	suspend fun comfirmChangePassword(@Body requestBody: RequestBody): String

	@GET("/api/collections/{colect}/records")
	suspend fun fetchList(
		@Path("colect") collection: String,
		@Query("page") page: Int = 1,
		@Query("perPage") perPage: Int = 30,
		@Query("sort") sort: String = "-created",
		): String

	@GET("/api/collections/{colect}/records")
	suspend fun search(
		@Path("colect") collection: String,
		@Query("page") page: Int = 1,
		@Query("perPage") perPage: Int = 30,
		@Query("sort") sort: String = "-created",
		@Query("filter") filter: String = "",
		): String


	@GET("/api/collections/{colect}/records/{id}")
	suspend fun fetchDetail(
		@Path("colect") collection: String,
		@Path("id") id: String,
		): String

	@GET("/api/collections/{colect}/records/{id}")
	suspend fun delete(
		@Path("colect") collection: String,
		@Path("id") id: String,
		): String

	@PATCH("/api/collections/{colect}/records/{id}")
	suspend fun update(
		@Path("colect") collection: String,
		@Path("id") id: String,
		@Body requestBody: RequestBody
	): String

	@POST("/api/collections/{colect}/records")
	suspend fun create(
		@Path("colect") collection: String,
		@Body requestBody: RequestBody
		): String

}