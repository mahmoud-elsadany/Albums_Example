package com.mahmoud.domain.usecases.homepage

import com.mahmoud.common.model.AlbumObject
import com.mahmoud.domain.repository.HomePageRepository
import com.mahmoud.domain.usecases.base.CompletableUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveAllAlbumsLocallyTask @Inject constructor(
    private val homePageRepository: HomePageRepository,
    defaultDispatcher: CoroutineDispatcher
) : CompletableUseCase<List<AlbumObject>>(defaultDispatcher) {


    override suspend fun generateSingle(input: List<AlbumObject>?) {
        if (input == null) {
            throw IllegalArgumentException("Post object cann't be null ")
        }
        return homePageRepository.saveNewLocalAlbums(input)
    }

}