package com.mahmoud.albums.application

import android.app.Application


import com.mahmoud.albums.util.general.Utils.getAppVersion
import com.mahmoud.albums.util.network.NetworkMonitoringUtil
import com.mahmoud.common.CommonConstants.APP_VERSION
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class ApplicationMain: Application(){

    var mNetworkMonitoringUtil: NetworkMonitoringUtil? = null
    override fun onCreate() {
        super.onCreate()


        APP_VERSION = getAppVersion(this)

        mNetworkMonitoringUtil = NetworkMonitoringUtil(this)
        mNetworkMonitoringUtil!!.checkNetworkState()
        mNetworkMonitoringUtil!!.registerNetworkCallbackEvents()


        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("albums.realm")
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)

    }


}