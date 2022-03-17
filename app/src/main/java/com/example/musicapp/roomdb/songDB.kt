package com.example.musicapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicapp.model.SongItem

@Database(entities = [SongDBItem::class],version=1, exportSchema = false)
abstract class songDB ():RoomDatabase() {

    abstract fun songDAO(): SongDAO

}
