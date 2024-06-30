package com.cme_mahmoud.data.repository.landingpage

import com.cme_mahmoud.common.Outcome
import com.cme_mahmoud.common.model.AlbumObject
import com.cme_mahmoud.common.model.AlbumsResponse
import com.cme_mahmoud.data.model.BaseApiResponse
import com.cme_mahmoud.data.repository.DatabaseDataSource
import com.cme_mahmoud.data.repository.PreferenceDataSource
import com.cme_mahmoud.data.repository.RemoteDataSource
import com.cme_mahmoud.domain.repository.HomePageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomePageRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: DatabaseDataSource,
    private val preferenceDataSource: PreferenceDataSource
) : HomePageRepository, BaseApiResponse() {


    override suspend fun getAllRemoteAlbums(): Flow<Outcome<AlbumsResponse>> {
        return flow {
            emit(Outcome.loading(true))
            val albums = safeApiCall {
                remoteDataSource.getRemoteAlbums()
            }
            emit(albums)
            emit(Outcome.loading(false))
        }.catch {
            emit(Outcome.failure(it))
            emit(Outcome.loading(false))
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun getAllLocalAlbums(): Flow<Outcome<List<AlbumObject>>> {
        return flow {
            emit(Outcome.loading(true))

            emit(Outcome.success(localDataSource.getAllAlbumsFromRealm()))

            emit(Outcome.loading(false))
        }.catch {
            emit(Outcome.failure(it))
            emit(Outcome.loading(false))
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun saveNewLocalAlbums( newRemoteAlbums: List<AlbumObject>) {
        localDataSource.deleteAllSavedAlbumsFromRealm()

        return localDataSource.saveMultipleAlbumsToRealm(
            newRemoteAlbums
        )
    }

    override suspend fun updateLocalAlbums( newRemoteAlbums: List<AlbumObject>) {
        val convertedToLocalAlbums: ArrayList<AlbumObject> = ArrayList()

        newRemoteAlbums.forEach() {
            val albumObject = AlbumObject(
                name = it.name,
                artist = it.artist,
                image = it.image,
                genre = it.genre,
                releaseDate = it.releaseDate,
                copyright = it.copyright,
                iTunesLink = it.iTunesLink
            )
            convertedToLocalAlbums.add(albumObject)
        }

        return localDataSource.updateAllAlbumsInRealm(
            convertedToLocalAlbums.toList()
        )
    }

    override suspend fun hasCachedAlbums(): Flow<Outcome<Boolean>> {

        return flow {
            emit(Outcome.loading(true))

            emit(Outcome.success(localDataSource.hasCachedAlbums()))

            emit(Outcome.loading(false))
        }.catch {
            emit(Outcome.failure(it))
            emit(Outcome.loading(false))
        }.flowOn(Dispatchers.IO)

    }

    override suspend fun deleteAllLocalAlbums() {
        return localDataSource.deleteAllSavedAlbumsFromRealm()
    }


}