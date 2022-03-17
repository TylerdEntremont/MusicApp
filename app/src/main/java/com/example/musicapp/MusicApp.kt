package com.example.musicapp

import android.app.Application
import com.example.musicapp.di.ApplicationModule
import com.example.musicapp.di.DaggerMusicComponent
import com.example.musicapp.di.MusicComponent

object MusicApp : Application() {

    var musicComponent :MusicComponent = DaggerMusicComponent
        .builder()
        .applicationModule(ApplicationModule(this))
        .build()

}