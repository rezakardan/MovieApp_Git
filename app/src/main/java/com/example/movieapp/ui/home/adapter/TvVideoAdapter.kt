package com.example.movieapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.databinding.ItemPopularBinding
import com.example.movieapp.databinding.ItemUpcomingBinding
import com.example.movieapp.utils.BANNER_COUNT
import com.example.movieapp.utils.ITEM_COUNT
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import com.example.movieapp.utils.extensions.toTwoDecimals
import javax.inject.Inject

class TvVideoAdapter @Inject constructor(): RecyclerView.Adapter<TvVideoAdapter.TvVideoViewHolder>() {

    lateinit var binding: ItemUpcomingBinding


    private var tvItems= emptyList<ResponseMovies.Result>()


    inner class TvVideoViewHolder(item: View): RecyclerView.ViewHolder(item){


        fun onBind(items: ResponseMovies.Result){

            binding.  txtTitle.text = items.name
//    carouselItemContainer.setOnMaskChangedListener { maskRect ->
//        txtTitle.translationX = maskRect.left
//        txtTitle.setAlpha(lerp(1F, 0F, 0F, 80F, maskRect.left))
//    }
            // val moviePosterURL = TMDB_IMAGE_BASE_URL_W780 + items.posterPath

            val url="$TMDB_IMAGE_BASE_URL_W780${items.posterPath}"

            binding.itemImg.loadImage(url)

            val vote = items.voteAverage!!.toTwoDecimals()
            binding.txtRating.text=vote






            binding.root.setOnClickListener {


                onItemClickListener?.let {
                    it(items)

                }



            }
        }


    }












    private var onItemClickListener:((ResponseMovies.Result)->Unit)?=null

    fun setOnItemClickListener(listener:(ResponseMovies.Result)->Unit){

        onItemClickListener=listener



    }




    fun setData(data:List<ResponseMovies.Result>){

        val diffUtils=DiffUtils(tvItems,data)
        val diff= DiffUtil.calculateDiff( diffUtils)

        tvItems=data

        diff.dispatchUpdatesTo(this)





    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvVideoViewHolder {
        binding= ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return           TvVideoViewHolder(binding.root)
    }


    override fun getItemViewType(position: Int)=position
    override fun getItemId(position: Int)=position.toLong()
    override fun getItemCount()= ITEM_COUNT

    override fun onBindViewHolder(holder: TvVideoViewHolder, position: Int) {
        holder.onBind(tvItems[position])
    }





    class DiffUtils(private val oldItem:List<ResponseMovies.Result>, private val newItem:List<ResponseMovies.Result>):
        DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition]===newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition]===newItem[newItemPosition]
        }


    }
}