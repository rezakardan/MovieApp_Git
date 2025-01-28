package com.example.movieapp.ui.allmovies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.home.ResponsePopular


import com.example.movieapp.databinding.ItemAllMoviesBinding
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import com.example.movieapp.utils.extensions.moneySeparating
import com.example.movieapp.utils.extensions.toTwoDecimals
import javax.inject.Inject

class TopRatedAdapter @Inject constructor() :
    PagingDataAdapter<ResponsePopular.Result, TopRatedAdapter.TopRatedViewHolder>(
        diffUtils
    ) {


    lateinit var binding: ItemAllMoviesBinding


    inner class TopRatedViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun setData(items: ResponsePopular.Result) {

            binding.  txtTitle.text = items.title


            val url="$TMDB_IMAGE_BASE_URL_W780${items.posterPath}"

            binding.imgPoster.loadImage(url)

            val vote = items.voteAverage!!.toTwoDecimals()
            binding.txtRating.text=vote






            binding.root.setOnClickListener {


                onItemClickListener?.let {
                    it(items)

                }



            }
        }


    }












    private var onItemClickListener:((ResponsePopular.Result)->Unit)?=null

    fun setOnItemClickListener(listener:(ResponsePopular.Result)->Unit){

        onItemClickListener=listener



    }


    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        holder.setData(getItem(position)!!)

        holder.setIsRecyclable(false)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {

        binding = ItemAllMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopRatedViewHolder(binding.root)


    }


    companion object {


        val diffUtils = object : DiffUtil.ItemCallback<ResponsePopular.Result>() {
            override fun areItemsTheSame(oldItem: ResponsePopular.Result, newItem:ResponsePopular. Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResponsePopular.Result, newItem: ResponsePopular.Result): Boolean {

                return oldItem == newItem
            }


        }


    }


}