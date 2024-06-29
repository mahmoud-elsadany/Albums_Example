package com.cme_mahmoud.albums.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {


    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}