package com.example.musicapp.roomdb


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.model.SongItem
import io.reactivex.Single

@Dao
interface SongDAO {
    @Query("SELECT * FROM songs WHERE genre IS (:genre)")
    fun getGenre (genre:String): Single<List<SongItem>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (songDBItem: SongDBItem)

    @Query("DELETE FROM songs WHERE genre IS (:genre) ")
    fun deleteGenre(genre:String)
}