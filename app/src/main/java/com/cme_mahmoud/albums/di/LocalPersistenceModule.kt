package com.cme_mahmoud.albums.di

import android.content.Context
import com.cme_mahmoud.data.repository.DatabaseDataSource
import com.cme_mahmoud.data.repository.PreferenceDataSource
import com.cme_mahmoud.local.source.PreferenceDataSourcempl
import com.cme_mahmoud.local.source.DatabaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
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
