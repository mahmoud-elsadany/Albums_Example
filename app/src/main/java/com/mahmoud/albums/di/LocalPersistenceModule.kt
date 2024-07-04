package com.mahmoud.albums.di

import android.content.Context
import com.mahmoud.data.repository.DatabaseDataSource
import com.mahmoud.data.repository.PreferenceDataSource
import com.mahmoud.local.source.PreferenceDataSourcempl
import com.mahmoud.local.source.DatabaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalPersistenceModule {


    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindsLocalDataSource(
            localDataSourceImpl: DatabaseDataSourceImpl
        ): DatabaseDataSource

    }




    @Provides
    @Singleton
    fun providesSharedPrefs(
        @ApplicationContext context: Context
    ): PreferenceDataSource = PreferenceDataSourcempl(context)


}
