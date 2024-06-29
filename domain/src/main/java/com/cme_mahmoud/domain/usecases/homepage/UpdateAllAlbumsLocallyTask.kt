package com.cme_mahmoud.domain.usecases.homepage

import com.cme_mahmoud.common.model.AlbumObject
import com.cme_mahmoud.domain.repository.HomePageRepository
import com.cme_mahmoud.domain.usecases.base.CompletableUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateAllAlbumsLocallyTask @Inject constructor(
    private val homePageRepository: HomePageRepository,
    defaultDispatcher: CoroutineDispatcher
) : CompletableUseCase<List<AlbumObject>>(defaultDispatcher) {


    override suspend fun generateSingle(input: List<AlbumObject>?) {
        if (input == null) {
            throw IllegalArgumentException("Post object cann't be null ")
        }
        return homePageRepository.updateLocalAlbums(input)
    }

}