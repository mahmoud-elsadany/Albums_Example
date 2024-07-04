package com.mahmoud.data.repository

import com.mahmoud.common.model.AlbumsResponse
import retrofit2.Response


interface RemoteDataSource {

    suspend fun getRemoteAlbums(): Response<AlbumsResponse>



}