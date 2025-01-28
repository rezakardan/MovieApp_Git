package com.example.movieapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponseTvOnTheAir
import com.example.movieapp.databinding.ItemPopularBinding
import com.example.movieapp.databinding.ItemUpcomingBinding
import com.example.movieapp.utils.ITEM_COUNT
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import com.example.movieapp.utils.extensions.toTwoDecimals
import javax.inject.Inject

class TvOnTheAirAdapter  @Inject constructor(): RecyclerView.Adapter<TvOnTheAirAdapter.TvOnTheAirViewHolder>() {

    lateinit var binding: ItemPopularBinding


    private var tvOnTheAirItem= emptyList<ResponseTvOnTheAir.Result>()


    inner class TvOnTheAirViewHolder(item: View): RecyclerView.ViewHolder(item){


        fun onBind(items: ResponseTvOnTheAir.Result){

            binding.  txtTitle.text = items.name


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












    private var onItemClickListener:((ResponseTvOnTheAir.Result)->Unit)?=null

    fun setOnItemClickListener(listener:(ResponseTvOnTheAir.Result)->Unit){

        onItemClickListener=listener



    }




    fun setData(data:List<ResponseTvOnTheAir.Result>){

        val diffUtils=DiffUtils(tvOnTheAirItem,data)
        val diff= DiffUtil.calculateDiff( diffUtils)

        tvOnTheAirItem=data

        diff.dispatchUpdatesTo(this)





    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvOnTheAirViewHolder {
        binding= ItemPopularBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return           TvOnTheAirViewHolder(binding.root)
    }


    override fun getItemViewType(position: Int)=position
    override fun getItemId(position: Int)=position.toLong()
    override fun getItemCount()= ITEM_COUNT

    override fun onBindViewHolder(holder: TvOnTheAirViewHolder, position: Int) {
        holder.onBind(tvOnTheAirItem[position])
    }





    class DiffUtils(private val oldItem:List<ResponseTvOnTheAir.Result>, private val newItem:List<ResponseTvOnTheAir.Result>):
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