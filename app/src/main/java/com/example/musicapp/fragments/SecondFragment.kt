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
import com.example.musicapp.databinding.FragmentSecondBinding
import com.example.musicapp.presenter.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), SongsViewContract2 {

    private var _binding: FragmentSecondBinding? = null

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

    private val songPresenter: SongsPresenterContract2 by lazy {
        SongPresenter2(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        MusicApp.musicComponent.inject(this)
        binding.secondSwiper.setOnRefreshListener {
            player.stop()
            player.reset()
            this.onPause()
            this.onResume()
            binding.secondSwiper.isRefreshing=false
        }

        binding.classicalList.apply {
            layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
            adapter = songAdaptor
        }

        songPresenter.checkNetwork()

        return binding.root

    }


    override fun onResume() {
        super.onResume()
        songPresenter.getSongs("CLASSIC")

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