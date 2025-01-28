package com.example.movieapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.home.ResponseUpComing
import com.example.movieapp.databinding.ItemUpcomingBinding
import com.example.movieapp.utils.ITEM_COUNT
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import com.example.movieapp.utils.extensions.toTwoDecimals
import javax.inject.Inject

class UpComingAdapter @Inject constructor() :
    RecyclerView.Adapter<UpComingAdapter.UpComingViewHolder>() {

    lateinit var binding: ItemUpcomingBinding


    private var tvItems = emptyList<ResponseUpComing.Result>()


    inner class UpComingViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(items: ResponseUpComing.Result) {

            binding.txtTitle.text = items.title


            val url = "$TMDB_IMAGE_BASE_URL_W780${items.posterPath}"

            binding.itemImg.loadImage(url)

            val vote = items.voteAverage!!.toTwoDecimals()
            binding.txtRating.text = vote






            binding.root.setOnClickListener {


                onItemClickListener?.let {
                    it(items)

                }


            }
        }


    }


    private var onItemClickListener: ((ResponseUpComing.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseUpComing.Result) -> Unit) {

        onItemClickListener = listener


    }


    fun setData(data: List<ResponseUpComing.Result>) {

        val diffUtils = DiffUtils(tvItems, data)
        val diff = DiffUtil.calculateDiff(diffUtils)

        tvItems = data

        diff.dispatchUpdatesTo(this)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingViewHolder {
        binding = ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpComingViewHolder(binding.root)
    }


    override fun getItemViewType(position: Int) = position
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemCount() = ITEM_COUNT

    override fun onBindViewHolder(holder: UpComingViewHolder, position: Int) {
        holder.onBind(tvItems[position])
    }


    class DiffUtils(
        private val oldItem: List<ResponseUpComing.Result>,
        private val newItem: List<ResponseUpComing.Result>
    ) :
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