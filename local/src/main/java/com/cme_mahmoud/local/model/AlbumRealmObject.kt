package com.cme_mahmoud.local.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class AlbumRealmObject(
    @PrimaryKey
    var id: String = "",

    var name: String = "",
    var artist: String = "",
    var image: String = "",
    var genre: String = "",
    var releaseDate: String = "",
    var copyright: String = "",
    var iTunesLink: String = ""

) : RealmObject()

