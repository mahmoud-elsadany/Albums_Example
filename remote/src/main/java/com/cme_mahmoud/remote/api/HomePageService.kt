package com.cme_mahmoud.remote.api

import com.cme_mahmoud.common.model.AlbumsResponse
import retrofit2.Response
import retrofit2.http.GET


interface HomePageService {

    @GET("us/music/most-played/10/albums.json")
    suspend fun getRemoteAlbums(
    ): Response<AlbumsResponse>



}