package com.cme_mahmoud.albums.di

import com.cme_mahmoud.common.CommonConstants
import com.cme_mahmoud.data.repository.RemoteDataSource
import com.cme_mahmoud.albums.BuildConfig
import com.cme_mahmoud.remote.api.HomePageService
import com.cme_mahmoud.remote.source.RemoteDataSourceImpl
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
    fun providesLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    fun providesLandingPageService(retrofit: Retrofit): HomePageService =
        retrofit.create(HomePageService::class.java)

    @Provides
    fun providesTrialsService(retrofit: Retrofit): TrialsService =
        retrofit.create(TrialsService::class.java)

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
                    .addHeader("Authorization", CommonConstants.TOKEN_VALUE)
                    .addHeader("appVersion", CommonConstants.APP_VERSION)
                    .addHeader("operatingSystem", "ANDROID")
                    .addHeader("Accept-Language","en")
                    .addHeader("module", CommonConstants.CHOSEN_MODULE)
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