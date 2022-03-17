package com.example.musicapp.fragments

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicapp.MusicApp
import com.example.musicapp.model.SongItem
import com.example.musicapp.adapter.SongAdapter
import com.example.musicapp.databinding.FragmentThirdBinding
import com.example.musicapp.presenter.*


class ThirdFragment: Fragment(), SongsViewContract3 {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

   private var player = MediaPlayer()

    private val songAdaptor by lazy {
        SongAdapter(onSongClick = {
            player.stop()
            player.reset()
            player.setDataSource(it.previewUrl)
            player.prepare()
            player.start()
        })

    }

    private val songPresenter: SongsPresenterContract3 by lazy {
        SongPresenter3(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MusicApp.musicComponent.inject(this)

        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        binding.thirdSwiper.setOnRefreshListener {
            player.stop()
            player.reset()
            this.onPause()
            this.onResume()
            binding.thirdSwiper.isRefreshing=false
        }

        binding.rockList.apply {
            layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
            adapter = songAdaptor
        }

        songPresenter.checkNetwork()

        return binding.root

    }


    override fun onResume() {
        super.onResume()
        songPresenter.getSongs("ROCK")

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun loadingSongs(isLoading: Boolean) {

    }

    override fun songsSuccess(songs: List<SongItem>) {
        songAdaptor.updateSongs(songs)
    }

    override fun songsError(throwable: Throwable) {
        AlertDialog.Builder(requireContext())
            .setTitle("AN ERROR HAS OCCURRED")
            .setMessage(throwable.localizedMessage)
            .setPositiveButton("DISMISS") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

}