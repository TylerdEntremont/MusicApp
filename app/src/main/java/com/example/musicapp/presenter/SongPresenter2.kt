package com.example.musicapp.presenter

import android.content.Context
import android.widget.Toast
import androidx.room.Room
import com.example.musicapp.model.SongItem
import com.example.musicapp.rest.NetworkUtils
import com.example.musicapp.rest.SongService
import com.example.musicapp.rest.SongsAPI
import com.example.musicapp.roomdb.DBOperations
import com.example.musicapp.roomdb.songDB
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SongPresenter2 @Inject constructor(
    private var context: Context?,
    private var viewContract: SongsViewContract2?,
    private val networkUtils: NetworkUtils = NetworkUtils(context),
    private val disposable: CompositeDisposable = CompositeDisposable(),
    private var songsAPI: SongsAPI?=null
) : SongsPresenterContract2 {

    private val songDB= context?.let { Room.databaseBuilder(it,songDB::class.java,"songs").allowMainThreadQueries().build() }

    override fun checkNetwork() {
        networkUtils.registerForNetworkState()
    }

    override fun getSongs(genre:String) {
        viewContract?.loadingSongs(true)

        networkUtils.networkState
            .subscribe(
                { netState -> if (netState) {
                    doNetworkCall(genre)
                } else {
                    if (songDB != null) {
                        DBOperations.collect(genre,songDB.songDAO())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { response -> viewContract?.songsSuccess(response) }
                            .apply {
                                disposable.add(this)
                            }
                    }
                } },
                { viewContract?.songsError(it) }
            ).apply {
                disposable.add(this)
            }
    }

    override fun destroy() {
        networkUtils.unregisterFromNetworkState()
        context = null
        viewContract = null
        disposable.dispose()
    }

    private fun doNetworkCall(genre:String) {
        SongService.retrofitService.getClassicSongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response -> viewContract?.songsSuccess(response.results)

                        if (songDB != null) {
                            DBOperations.insert(response.results,genre,songDB.songDAO())
                        } },
                    { error -> viewContract?.songsError(error) }
                ).apply {
                    disposable.add(this)
                }


    }
}

interface SongsViewContract2 {
    fun loadingSongs (isLoading: Boolean)
    fun songsSuccess(songs:List<SongItem>)
    fun songsError(throwable: Throwable)
}

interface SongsPresenterContract2 {
    fun getSongs(genre:String)
    fun destroy()
    fun checkNetwork()
}