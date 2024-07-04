package com.mahmoud.albums.di


import com.mahmoud.data.repository.landingpage.HomePageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {



    @Binds
    abstract fun bindsLandingPageRepository(
        repoImpl: HomePageRepositoryImpl
    ): com.mahmoud.domain.repository.HomePageRepository



}