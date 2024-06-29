package com.cme_mahmoud.local.database.realm

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

object RealmModuleInitializer {
    fun initializeRealm(context: Context) {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .name("CME_Albums.realm")
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}