package com.mahmoud.remote.source


import com.mahmoud.common.model.AlbumsResponse
import com.mahmoud.data.repository.RemoteDataSource
import com.mahmoud.remote.api.HomePageService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val homePageService: HomePageService
) : RemoteDataSource {

    override suspend fun getRemoteAlbums(): Response<AlbumsResponse> {
        return homePageService.getRemoteAlbums()
    }


}
