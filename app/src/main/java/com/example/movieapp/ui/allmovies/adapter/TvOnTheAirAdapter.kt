package com.example.movieapp.ui.allmovies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponseTvOnTheAir
import com.example.movieapp.databinding.ItemAllMoviesBinding
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import com.example.movieapp.utils.extensions.moneySeparating
import com.example.movieapp.utils.extensions.toTwoDecimals
import javax.inject.Inject

class TvOnTheAirAdapter@Inject constructor():
    PagingDataAdapter<ResponseTvOnTheAir.Result, TvOnTheAirAdapter.TvOnTheAirViewHolder>(diffUtils) {


    lateinit var binding: ItemAllMoviesBinding


       inner class TvOnTheAirViewHolder(item: View):RecyclerView.ViewHolder(item){

           fun onBind(items:ResponseTvOnTheAir.Result){


               binding.apply {

                   items.let {


                       val url = "$TMDB_IMAGE_BASE_URL_W780${it.posterPath}"
                       imgPoster.loadImage(url)


                       txtTitle.text = it.name

                       txtFirstDate.text = it.firstAirDate

                       txtVoteCount.text = it.voteCount.moneySeparating()

                       val vote = it.voteAverage.toTwoDecimals()
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


    private var onItemClickListener: ((ResponseTvOnTheAir.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseTvOnTheAir.Result) -> Unit) {

        onItemClickListener = listener

    }



    override fun onBindViewHolder(holder: TvOnTheAirViewHolder, position: Int) {
        holder.onBind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvOnTheAirViewHolder {
        binding=ItemAllMoviesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TvOnTheAirViewHolder(binding.root)
    }








    companion object {


        val diffUtils = object : DiffUtil.ItemCallback<ResponseTvOnTheAir.Result>() {
            override fun areItemsTheSame(oldItem: ResponseTvOnTheAir.Result, newItem: ResponseTvOnTheAir.Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResponseTvOnTheAir.Result, newItem: ResponseTvOnTheAir.Result): Boolean {

                return oldItem == newItem
            }




        }


    }


}