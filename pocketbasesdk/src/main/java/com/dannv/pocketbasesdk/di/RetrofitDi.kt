package com.dannv.pocketbasesdk.di

import com.dannv.pocketbasesdk.api.PocketBaseServices
import com.dannv.pocketbasesdk.impl.PocketbaseImpl
import com.dannv.pocketbasesdk.storge.PocketBaseSharePrefUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

	@Provides
	@Singleton
	fun provideLoggingInterceptor(): HttpLoggingInterceptor {
		return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
	}

	@Singleton
	@Provides
	fun providerLogger(interceptor: HttpLoggingInterceptor): OkHttpClient {
		val client = OkHttpClient.Builder()
		client.addInterceptor(interceptor)
		client.addInterceptor(Interceptor { chain: Interceptor.Chain ->
			val original: Request = chain.request()

			val request: Request.Builder = original.newBuilder()
			if (token.isNotEmpty()) {
				request.addHeader("Authorization:", "$token")
			}
			chain.proceed(request.build())
		})
		return client.build()
	}

	@Singleton
	@Provides
	fun providerRetrofit(httpClient: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.baseUrl("https://androidsdk.fly.dev")
			.addConverterFactory(ScalarsConverterFactory.create())
			.client(httpClient)
			.build()
	}

	// Method to set the new base URL dynamically
	fun setBaseUrl(newBaseUrl: String) {
		val newRetrofit = providerRetrofit(providerLogger(provideLoggingInterceptor()))
			.newBuilder()
			.baseUrl(newBaseUrl)
			.build()
		providerServices(newRetrofit)
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