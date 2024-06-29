package com.cme_mahmoud.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.cme_mahmoud.common.ErrorModel
import com.cme_mahmoud.common.Outcome
import com.cme_mahmoud.common.model.AlbumObject
import com.cme_mahmoud.domain.usecases.homepage.GetAllAlbumsLocallyTask
import com.cme_mahmoud.domain.usecases.homepage.GetAllAlbumsRemoteTask
import com.cme_mahmoud.domain.usecases.homepage.HasCachedAlbumsLocallyTask
import com.cme_mahmoud.domain.usecases.homepage.SaveAllAlbumsLocallyTask
import com.cme_mahmoud.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val getAllAlbumsLocallyTask: GetAllAlbumsLocallyTask,
    private val saveAllAlbumsLocallyTask: SaveAllAlbumsLocallyTask,
    private val getAllAlbumsRemoteTask: GetAllAlbumsRemoteTask,
    private val hasCachedAlbumsLocallyTask: HasCachedAlbumsLocallyTask,

    ) : BaseViewModel() {


    val cachedAlbums = MutableSharedFlow<List<AlbumObject>>()


    fun hasCachedAlbums() {
        viewModelScope.launch {
            hasCachedAlbumsLocallyTask.buildUseCase("")
                .catch {
                    exceptionState.emit(it.message ?: "")
                }.flowOn(Dispatchers.IO)
                .collect {
                    when (it) {
                        is Outcome.Progress -> {
                            loadingState.emit(it.loading)
                        }

                        is Outcome.Failure -> {
                            exceptionState.emit(it.e.message ?: "")
                        }

                        is Outcome.ApiError -> {
                            apiErrorState.emit(ErrorModel(it.errorCode, it.errorMessage))
                        }

                        is Outcome.Success -> {
                            if (it.data) {
                                getCachedAlbums()
                            } else {
                                getAllRemoteAlbums()
                            }
                        }
                    }
                }
        }
    }

    fun getAllRemoteAlbums() {
        viewModelScope.launch {
            getAllAlbumsRemoteTask.buildUseCase("")
                .catch {
                    exceptionState.emit(it.message ?: "")
                }.flowOn(Dispatchers.IO)
                .collect {
                    when (it) {
                        is Outcome.Progress -> {
                            loadingState.emit(it.loading)
                        }

                        is Outcome.Failure -> {
                            exceptionState.emit(it.e.message ?: "")
                        }

                        is Outcome.ApiError -> {
                            apiErrorState.emit(ErrorModel(it.errorCode, it.errorMessage))
                        }

                        is Outcome.Success -> {

                            val albumList: ArrayList<AlbumObject> = ArrayList()

                            it.data.feed.results.forEach() { albumListObj ->
                                albumList.add(
                                    AlbumObject(
                                        name = albumListObj.name,
                                        artist = albumListObj.artistName,
                                        image = albumListObj.artworkUrl100,
                                        genre = albumListObj.genres.filter { it.genreId != "34" }
                                            .joinToString(),
                                        releaseDate = albumListObj.releaseDate,
                                        copyright = it.data.feed.copyright,
                                        iTunesLink = albumListObj.url
                                    )
                                )

                            }

                            saveNewAlbums(albumList)

                        }
                    }
                }
        }
    }

    fun saveNewAlbums(newRemoteAlbums: List<AlbumObject>) {
        viewModelScope.launch {
            saveAllAlbumsLocallyTask.buildUseCase(newRemoteAlbums)

            cachedAlbums.emit(newRemoteAlbums)
        }
    }

    fun getCachedAlbums() {
        viewModelScope.launch {
            getAllAlbumsLocallyTask.buildUseCase("")
                .catch {
                    exceptionState.emit(it.message ?: "")
                }.flowOn(Dispatchers.IO)
                .collect {
                    when (it) {
                        is Outcome.Progress -> {
                            loadingState.emit(it.loading)
                        }

                        is Outcome.Failure -> {
                            exceptionState.emit(it.e.message ?: "")
                        }

                        is Outcome.ApiError -> {
                            apiErrorState.emit(ErrorModel(it.errorCode, it.errorMessage))
                        }

                        is Outcome.Success -> {

                            cachedAlbums.emit(it.data)

                        }
                    }
                }
        }
    }


}