package com.mahmoud.albums.di

import com.mahmoud.common.CommonConstants
import com.mahmoud.data.repository.RemoteDataSource
import com.mahmoud.albums.BuildConfig
import com.mahmoud.remote.api.HomePageService
import com.mahmoud.remote.source.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


@Module(includes = [RemoteModule.Binders::class])
@InstallIn(SingletonComponent::class)
class RemoteModule {
    @InstallIn(SingletonComponent::class)
    @Module
    interface Binders {

        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: RemoteDataSourceImpl
        ): RemoteDataSource


    }


    @Provides
    fun providesHomePageService(retrofit: Retrofit): HomePageService =
        retrofit.create(HomePageService::class.java)


    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            .protocols(listOf(Protocol.HTTP_1_1))
            .hostnameVerifier { hostname, session -> true }
            .readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
            .callTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("appVersion", CommonConstants.APP_VERSION)
                    .addHeader("operatingSystem", "ANDROID")
                    .addHeader("Accept-Language","en")
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })

        return client.build()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()

}