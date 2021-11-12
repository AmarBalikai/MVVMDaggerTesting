package com.code.mvvmdaggertesting.networking

import com.code.mvvmdaggertesting.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RemoteDataSource @Inject constructor(){
    companion object {
        private const val BASE_URL = "https://swapi.dev/api/"
    }

    fun <Api> buildApi(
        api: Class<Api>
    ): Api {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BASIC
                    client.addInterceptor(logging)
                }
            }
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}