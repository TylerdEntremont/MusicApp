package com.example.musicapp.roomdb

import com.example.musicapp.model.SongItem
import io.reactivex.Single

object DBOperations {


    fun insert(songs: List<SongItem>, genre: String, songDAO:SongDAO) {
        songDAO.deleteGenre(genre)
        for (song in songs) {
            val songDBItem = SongDBItem(0,song, genre)
            songDAO.insert(songDBItem)
        }
    }

    fun collect(genre: String, songDAO:SongDAO): Single<List<SongItem>> {
        return songDAO.getGenre(genre)
    }

}