package com.dannv.pocketbasesdk.di

import android.content.Context
import com.dannv.pocketbasesdk.R
import com.dannv.pocketbasesdk.api.PocketBaseServices
import com.dannv.pocketbasesdk.api.PocketConstants
import com.dannv.pocketbasesdk.impl.PocketbaseImpl
import com.dannv.pocketbasesdk.storge.PocketBaseSharePrefUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitDi {
	private var token: String = ""

	fun setToken() {
		token = PocketBaseSharePrefUtils.token ?: ""
	}

	private var isLog = false
	fun setLogger(isLoG: Boolean) {
		isLog = isLoG
	}
	@Provides
	@Singleton
	fun provideLoggingInterceptor(): HttpLoggingInterceptor {
		return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
	}

	@Singleton
	@Provides
	fun providerLogger(interceptor: HttpLoggingInterceptor): OkHttpClient {
		val client = OkHttpClient.Builder()
		if (isLog) {
			client.addInterceptor(interceptor)
		}
		client.addInterceptor(Interceptor { chain: Interceptor.Chain ->
			val original: Request = chain.request()

			val request: Request.Builder = original.newBuilder()
			if (token.isNotEmpty()) {
				request.addHeader("Authorization", "$token")
			}
			chain.proceed(request.build())
		})
		return client.build()
	}

	@Singleton
	@Provides
	fun providerRetrofit(@ApplicationContext context : Context, httpClient: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.baseUrl(if (PocketConstants.URL_BASE_URL.isNotEmpty()) PocketConstants.URL_BASE_URL else context.getString(R.string.base_url))
			.addConverterFactory(ScalarsConverterFactory.create())
			.client(httpClient)
			.build()
	}

	@Singleton
	@Provides
	fun providerServices(retrofit: Retrofit): PocketBaseServices {
		return retrofit.create(PocketBaseServices::class.java)
	}

	@Singleton
	@Provides
	fun providerPocketBase(pocketbase: PocketBaseServices): PocketbaseImpl {
		return PocketbaseImpl(pocketbase)
	}


}