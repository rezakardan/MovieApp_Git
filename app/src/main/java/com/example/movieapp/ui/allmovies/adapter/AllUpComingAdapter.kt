package com.example.movieapp.ui.allmovies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.data.model.home.ResponseUpComing
import com.example.movieapp.databinding.ItemAllMoviesBinding
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import com.example.movieapp.utils.extensions.moneySeparating
import com.example.movieapp.utils.extensions.toTwoDecimals
import javax.inject.Inject

class AllUpComingAdapter@Inject constructor():PagingDataAdapter<ResponseUpComing.Result, AllUpComingAdapter.TvListViewHolder>(diffUtils) {


    lateinit var binding:ItemAllMoviesBinding

    inner class TvListViewHolder(item: View):RecyclerView.ViewHolder(item){

        fun onBind(items:ResponseUpComing.Result){


            binding.apply {

                items.let {


                    val url = "$TMDB_IMAGE_BASE_URL_W780${it.posterPath}"
                    imgPoster.loadImage(url)


                    txtTitle.text = it.title

                    txtFirstDate.text = it.releaseDate

                    txtVoteCount.text = it.voteCount?.moneySeparating()

                    val vote = it.voteAverage!!.toTwoDecimals()
                    txtRating.text = vote


                }
                root.setOnClickListener {


                    onItemClickListener?.let {
                        it(items)
                    }


                }
            }





        }



    }



    private var onItemClickListener: ((ResponseUpComing.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseUpComing.Result) -> Unit) {

        onItemClickListener = listener

    }




    override fun onBindViewHolder(holder: TvListViewHolder, position: Int) {
        holder.onBind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvListViewHolder {
        binding=ItemAllMoviesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TvListViewHolder(binding.root)
    }








    companion object {


        val diffUtils = object : DiffUtil.ItemCallback<ResponseUpComing.Result>() {
            override fun areItemsTheSame(oldItem: ResponseUpComing.Result, newItem: ResponseUpComing.Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResponseUpComing.Result, newItem: ResponseUpComing.Result): Boolean {

                return oldItem == newItem
            }




        }


    }



}