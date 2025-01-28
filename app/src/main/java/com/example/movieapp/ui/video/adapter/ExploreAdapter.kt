package com.example.movieapp.ui.video.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.movie.MovieData
import com.example.movieapp.databinding.ItemExploreBinding
import com.example.movieapp.utils.extensions.disableWebViewsBackground
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import javax.inject.Inject

class ExploreAdapter @Inject constructor(private val lifecycle: Lifecycle) :
    RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    lateinit var binding: ItemExploreBinding


    private var bannerItem = emptyList<MovieData>()


    inner class ExploreViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private var youTubePlayer: YouTubePlayer? = null
        fun onBind(items: MovieData) {

            binding.apply {


                lifecycle.addObserver(youtubePlayerView)

                overlayView.setOnClickListener {

                    youTubePlayer?.play()

                    youtubePlayerView.disableWebViewsBackground()

                    youtubePlayerView.addYouTubePlayerListener(

                        object : AbstractYouTubePlayerListener() {


                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)


                                this@ExploreViewHolder.youTubePlayer = youTubePlayer


                                items.movieId.let { youTubePlayer.cueVideo(it, 0f) }


                            }


                            override fun onStateChange(
                                youTubePlayer: YouTubePlayer,
                                state: PlayerConstants.PlayerState
                            ) {
                                super.onStateChange(youTubePlayer, state)



                                when (state) {


                                    PlayerConstants.PlayerState.VIDEO_CUED -> {
                                        overlayView.visibility = View.VISIBLE
                                    }


                                    else -> {
                                        overlayView.visibility = View.GONE
                                    }


                                }
                            }


                        }


                    )


                }


            }


        }


    }


    private var onItemClickListener: ((MovieData) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieData) -> Unit) {

        onItemClickListener = listener


    }


    fun setData(data: List<MovieData>) {

        val diffUtils = DiffUtils(bannerItem, data)
        val diff = DiffUtil.calculateDiff(diffUtils)

        bannerItem = data

        diff.dispatchUpdatesTo(this)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        binding = ItemExploreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExploreViewHolder(binding.root)
    }


    override fun getItemViewType(position: Int) = position
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemCount() = bannerItem.size

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        holder.onBind(bannerItem[position])
    }


    class DiffUtils(private val oldItem: List<MovieData>, private val newItem: List<MovieData>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }


    }
}