package com.cme_mahmoud.remote.source


import com.cme_mahmoud.common.model.AlbumsResponse
import com.cme_mahmoud.data.repository.RemoteDataSource
import com.cme_mahmoud.remote.api.HomePageService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val homePageService: HomePageService
) : RemoteDataSource {

    override suspend fun getRemoteAlbums(): Response<AlbumsResponse> {
        return homePageService.getRemoteAlbums()
    }


}
