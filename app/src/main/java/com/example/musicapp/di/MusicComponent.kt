package com.example.musicapp.di


import com.example.musicapp.MainActivity
import com.example.musicapp.fragments.FirstFragment
import com.example.musicapp.fragments.SecondFragment
import com.example.musicapp.fragments.ThirdFragment
import dagger.Component

@Component(
    modules = [
        NetworkModule::class,
        ApplicationModule::class
    ]
)
interface MusicComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(firstFragment: FirstFragment)
    fun inject(secondFragment: SecondFragment)
    fun inject(thirdFragment: ThirdFragment)
}