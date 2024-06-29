package com.cme_mahmoud.albums.application

import android.app.Application


import com.cme_mahmoud.albums.util.general.Utils.getAppVersion
import com.cme_mahmoud.albums.util.network.NetworkMonitoringUtil
import com.cme_mahmoud.common.CommonConstants.APP_VERSION
import com.cme_mahmoud.local.database.realm.RealmModuleInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationMain: Application(){

    var mNetworkMonitoringUtil: NetworkMonitoringUtil? = null
    override fun onCreate() {
        super.onCreate()


        APP_VERSION = getAppVersion(this)

        mNetworkMonitoringUtil = NetworkMonitoringUtil(this)
        mNetworkMonitoringUtil!!.checkNetworkState()
        mNetworkMonitoringUtil!!.registerNetworkCallbackEvents()


        RealmModuleInitializer.initializeRealm(this)

    }


}