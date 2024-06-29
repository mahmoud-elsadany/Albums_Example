package com.cme_mahmoud.data.repository

import com.cme_mahmoud.common.model.AlbumsResponse
import retrofit2.Response


interface RemoteDataSource {

    suspend fun getRemoteAlbums(): Response<AlbumsResponse>



}