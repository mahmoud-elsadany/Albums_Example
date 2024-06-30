package com.cme_mahmoud.domain.usecases.homepage

import com.cme_mahmoud.common.Outcome

import com.cme_mahmoud.domain.repository.HomePageRepository
import com.cme_mahmoud.domain.usecases.base.FlowableUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HasCachedAlbumsLocallyTask  @Inject constructor(
    private val homePageRepository: HomePageRepository,
    defaultDispatcher: CoroutineDispatcher
) : FlowableUseCase<Boolean, String>(
defaultDispatcher
) {


    override suspend fun generateFlowable(
        input: String?,
        networStatus: Boolean
    ): Flow<Outcome<Boolean>> {
        return homePageRepository.hasCachedAlbums()
    }

}