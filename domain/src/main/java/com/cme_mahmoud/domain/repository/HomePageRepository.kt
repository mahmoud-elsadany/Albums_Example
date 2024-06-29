package com.cme_mahmoud.domain.repository

import com.cme_mahmoud.common.Outcome
import com.cme_mahmoud.common.model.AlbumObject
import com.cme_mahmoud.common.model.AlbumsResponse
import kotlinx.coroutines.flow.Flow

interface HomePageRepository {

    suspend fun getAllLocalAlbums(): Flow<Outcome<List<AlbumObject>>>

    suspend fun getAllRemoteAlbums(): Flow<Outcome<AlbumsResponse>>


    suspend fun saveNewLocalAlbums(newRemoteAlbums: List<AlbumObject>)

    suspend fun updateLocalAlbums(newRemoteAlbums: List<AlbumObject>)

    suspend fun deleteAllLocalAlbums()


    suspend fun hasCachedAlbums(): Flow<Outcome<Boolean>>



}