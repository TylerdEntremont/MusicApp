package com.example.musicapp.rest

import com.example.musicapp.model.SongItemX
import io.reactivex.Single
import retrofit2.http.GET

interface SongsAPI {
    @GET("search?term=rock&amp;media=music&amp;entity=song&amp;limit=50")
    fun getRockSongs(): Single<SongItemX>

    @GET("search?term=pop&amp;media=music&amp;entity=song&amp;limit=50")
    fun getPopSongs(): Single<SongItemX>

    @GET ("search?term=classick&amp;media=music&amp;entity=song&amp;limit=50")
    fun getClassicSongs(): Single<SongItemX>

}