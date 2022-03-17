package com.example.musicapp.fragments

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicapp.MusicApp
import com.example.musicapp.adapter.SongAdapter
import com.example.musicapp.databinding.FragmentFirstBinding
import com.example.musicapp.model.SongItem
import com.example.musicapp.presenter.SongPresenter
import com.example.musicapp.presenter.SongsPresenterContract
import com.example.musicapp.presenter.SongsViewContract


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), SongsViewContract{

    private var _binding: FragmentFirstBinding? = null

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

    private val songPresenter: SongsPresenterContract by lazy {
        SongPresenter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        MusicApp.musicComponent.inject(this)
        binding.firstSwiper.setOnRefreshListener {
            player.stop()
            player.start()
            this.onPause()
            this.onResume()
            binding.firstSwiper.isRefreshing=false
        }
        binding.popList.apply {
            layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
            adapter = songAdaptor
        }

        songPresenter.checkNetwork()

        return binding.root

    }


    override fun onResume() {
        super.onResume()

        songPresenter.getSongs("POP")

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