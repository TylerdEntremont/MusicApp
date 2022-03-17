package com.example.musicapp.roomdb

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicapp.model.SongItem

@Entity(tableName="songs")
data class SongDBItem(
    @PrimaryKey (autoGenerate = true) val sid:Int,
    @Embedded val song:SongItem?,
    @ColumnInfo val genre:String?
)
