package com.example.musicapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.musicapp.R
import com.example.musicapp.model.SongItem
import com.squareup.picasso.Picasso


class SongAdapter (
    private val songs: MutableList<SongItem> = mutableListOf(),
    private val onSongClick: (SongItem) -> Unit
        ):RecyclerView.Adapter<SongsViewHolder>(){

    fun updateSongs (newSongs: List<SongItem>){
        songs.clear()
        songs.addAll(newSongs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val songView = LayoutInflater.from(parent.context).inflate(R.layout.music_item,parent,false)
        return SongsViewHolder(songView,onSongClick)
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val songItem = songs[position]
        holder.bind(songItem)
    }

    override fun getItemCount(): Int =songs.size


}


class SongsViewHolder(
    itemView: View,
    private val onSongClicked: (SongItem) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val songName: TextView =itemView.findViewById(R.id.songName)
    private val bandName: TextView = itemView.findViewById(R.id.bandName)
    private val price: TextView = itemView.findViewById(R.id.price)
    private val songArt: ImageView = itemView.findViewById((R.id.songArt))

    fun bind(song: SongItem){

        songName.text = song.trackName
        bandName.text = song.artistName
        price.text = song.trackPrice.toString() + song.currency

        itemView.setOnClickListener{
            onSongClicked.invoke(song)
        }

        Picasso.get()
            .load(song.artworkUrl60)
            .placeholder(R.drawable.ic_baseline_image)
            .error(R.drawable.ic_baseline_image)
            .resize(250, 250)
            .into(songArt)


    }


}