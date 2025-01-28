package com.example.movieapp.ui.allmovies.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.databinding.ItemAllMoviesBinding
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import com.example.movieapp.utils.extensions.moneySeparating
import com.example.movieapp.utils.extensions.toTwoDecimals
import javax.inject.Inject

class TvListAdapter@Inject constructor():PagingDataAdapter<ResponseMovies.Result, TvListAdapter.TvListViewHolder>(diffUtils) {


    lateinit var binding:ItemAllMoviesBinding

    inner class TvListViewHolder(item: View):RecyclerView.ViewHolder(item){

        fun onBind(items:ResponseMovies.Result){


            binding.apply {

                items.let {


                    val url = "$TMDB_IMAGE_BASE_URL_W780${it.posterPath}"
                    imgPoster.loadImage(url)


                    txtTitle.text = it.name

                    txtFirstDate.text = it.firstAirDate

                    txtVoteCount.text = it.voteCount?.moneySeparating()

                    val vote = it.voteAverage!!.toTwoDecimals()
                    txtRating.text = vote


                }
                root.setOnClickListener {


                    onItemClickListener?.let {
                        it(items)


                     //   Log.d("Recycler", "ID: " + items.id)
                    }






                }
            }





        }



    }



    private var onItemClickListener: ((ResponseMovies.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseMovies.Result) -> Unit) {

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


        val diffUtils = object : DiffUtil.ItemCallback<ResponseMovies.Result>() {
            override fun areItemsTheSame(oldItem: ResponseMovies.Result, newItem: ResponseMovies.Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResponseMovies.Result, newItem: ResponseMovies.Result): Boolean {

                return oldItem == newItem
            }




        }


    }



}