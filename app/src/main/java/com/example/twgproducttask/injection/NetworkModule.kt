package com.campgladiator.injection

import android.app.Application

import com.example.twgproducttask.network.interceptors.service.WarehouseService
import com.example.twgproducttask.util.Constants
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule(private val application: Application) {

    @Provides
    @Singleton
    @Named("twgOkHttpClient")
    fun providesOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val request =
                chain.request().newBuilder().addHeader("Ocp-Apim-Subscription-Key", Constants.SUBSCRIPTION_KEY).build()
            chain.proceed(request)
        }
        builder.addInterceptor(loggingInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    @Named("twgRetrofit")
    fun providesRetrofit(@Named("twgOkHttpClient") okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.HTTP_URL_ENDPOINT)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideWareHouseService(@Named("twgRetrofit") retrofit: Retrofit): WarehouseService {
        return retrofit.create(WarehouseService::class.java)
    }

    @Provides
    fun getGSONConverter(): GsonConverterFactory = GsonConverterFactory.create(GsonBuilder().create())

}
