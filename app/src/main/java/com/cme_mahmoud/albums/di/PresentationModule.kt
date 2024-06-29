package com.cme_mahmoud.albums.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cme_mahmoud.presentation.base.BaseViewModel
import com.cme_mahmoud.presentation.factory.ViewModelFactory
import com.cme_mahmoud.presentation.viewmodel.HomePageViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap


@Module
@InstallIn(SingletonComponent::class)
abstract class PresentationModule {

    @Binds
    abstract fun bindsViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(HomePageViewModel::class)
    abstract fun bindsHomePageViewModel(homePageVM: HomePageViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel::class)
    abstract fun bindsBaseViewModel(baseVM: BaseViewModel): ViewModel





}