package com.mahmoud.domain.usecases.homepage

import com.mahmoud.common.Outcome
import com.mahmoud.common.model.AlbumsResponse
import com.mahmoud.domain.repository.HomePageRepository
import com.mahmoud.domain.usecases.base.FlowableUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllAlbumsRemoteTask @Inject constructor(
    private val homePageRepository: HomePageRepository,
    defaultDispatcher: CoroutineDispatcher
) : FlowableUseCase<AlbumsResponse, String>(
    defaultDispatcher
) {

    override suspend fun generateFlowable(
        input: String?,
        networStatus: Boolean
    ): Flow<Outcome<AlbumsResponse>> {
        if (input == null) {
            throw IllegalArgumentException("Post object cann't be null ")
        }
        return homePageRepository.getAllRemoteAlbums()
    }

}