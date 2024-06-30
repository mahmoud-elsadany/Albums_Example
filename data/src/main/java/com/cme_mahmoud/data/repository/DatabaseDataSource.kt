package com.cme_mahmoud.data.repository

import com.cme_mahmoud.common.model.AlbumObject


interface DatabaseDataSource {


    fun getAllAlbumsFromRealm(): List<AlbumObject>
    fun deleteAllSavedAlbumsFromRealm()
    fun hasCachedAlbums(): Boolean

    fun saveMultipleAlbumsToRealm(albums: List<AlbumObject>)
    fun updateAllAlbumsInRealm(updatedAlbums: List<AlbumObject>)

}