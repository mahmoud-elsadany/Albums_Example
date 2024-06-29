package com.cme_mahmoud.domain.usecases.homepage

import com.cme_mahmoud.common.Outcome
import com.cme_mahmoud.common.model.AlbumObject
import com.cme_mahmoud.domain.repository.HomePageRepository
import com.cme_mahmoud.domain.usecases.base.FlowableUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAlbumsLocallyTask @Inject constructor(
    private val homePageRepository: HomePageRepository,
    defaultDispatcher: CoroutineDispatcher
) : FlowableUseCase<List<AlbumObject>, String>(
    defaultDispatcher
) {

    override suspend fun generateFlowable(
        input: String?,
        networkStatus: Boolean
    ): Flow<Outcome<List<AlbumObject>>> {

        return homePageRepository.getAllLocalAlbums()
    }

}