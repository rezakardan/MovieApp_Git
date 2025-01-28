package com.example.movieapp.ui.genre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.genre.ResponseMoviesListByGenre
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.databinding.ItemAllMoviesBinding
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import com.example.movieapp.utils.extensions.moneySeparating
import com.example.movieapp.utils.extensions.toTwoDecimals
import javax.inject.Inject

class GenreMoviesAdapter @Inject constructor() :
    PagingDataAdapter<ResponseMoviesListByGenre.Result,GenreMoviesAdapter.GenreMoviesViewHolder>(diffUtils) {


    lateinit var binding: ItemAllMoviesBinding








    inner class GenreMoviesViewHolder(item: View) : RecyclerView.ViewHolder(item) {




        fun onBind(items:ResponseMoviesListByGenre.Result){




            binding.apply {

                items.let {


                    val url = "$TMDB_IMAGE_BASE_URL_W780${it.posterPath}"
                    imgPoster.loadImage(url)


                    txtTitle.text = it.title

                    txtFirstDate.text = it.releaseDate

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


    private var onItemClickListener: ((ResponseMoviesListByGenre.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseMoviesListByGenre.Result) -> Unit) {

        onItemClickListener = listener

    }





    override fun onBindViewHolder(holder: GenreMoviesViewHolder, position: Int) {

        holder.onBind(getItem(position)!!)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreMoviesViewHolder {


        binding = ItemAllMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GenreMoviesViewHolder(binding.root)


    }




   companion object{


       val diffUtils=object:DiffUtil.ItemCallback<ResponseMoviesListByGenre.Result>(){
           override fun areItemsTheSame(
               oldItem: ResponseMoviesListByGenre.Result,
               newItem: ResponseMoviesListByGenre.Result
           ): Boolean {
               return oldItem.id==newItem.id
           }

           override fun areContentsTheSame(
               oldItem: ResponseMoviesListByGenre.Result,
               newItem: ResponseMoviesListByGenre.Result
           ): Boolean {
               return oldItem==newItem
           }
       }



   }






}