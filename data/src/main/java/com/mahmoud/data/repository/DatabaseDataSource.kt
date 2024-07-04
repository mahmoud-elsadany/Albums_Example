package com.mahmoud.data.repository

import com.mahmoud.common.model.AlbumObject


interface DatabaseDataSource {


    fun getAllAlbumsFromRealm(): List<AlbumObject>
    fun deleteAllSavedAlbumsFromRealm()
    fun hasCachedAlbums(): Boolean

    fun saveMultipleAlbumsToRealm(albums: List<AlbumObject>)
    fun updateAllAlbumsInRealm(updatedAlbums: List<AlbumObject>)

}