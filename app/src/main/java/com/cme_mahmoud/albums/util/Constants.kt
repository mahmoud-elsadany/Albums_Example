package com.cme_mahmoud.albums.util

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.cme_mahmoud.common.model.landingpage.ModuleConfig

public object Constants {

    //APP config
    const val APP_CATEGORY = "diseases"

    //LOGIN constants
    const val TENANT = "tenant"
    const val RC_SIGN_IN = 301
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser: FirebaseUser? = firebaseAuth.currentUser


    //custom camera constants
    const val FILE_NAME_FORMAT = "yy-MM-dd-HH-mm-ss-SSS"
    var CAPTURE_IMG_URI: Uri? = null


    //Landing page
    var modelNameAndVersion = ""
    var currModuleConfig: ModuleConfig? = null
    var currentMLModelPath: String? = ""


}