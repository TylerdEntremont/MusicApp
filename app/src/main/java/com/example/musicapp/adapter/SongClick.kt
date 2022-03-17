package com.example.musicapp.adapter

import com.example.musicapp.model.SongItem

interface SongClick {
    fun onSongClick(song: SongItem)
}