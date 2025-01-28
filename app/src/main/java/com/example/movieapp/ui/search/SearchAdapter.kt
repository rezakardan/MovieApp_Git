package com.example.movieapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.detail.ResponseSimilar
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.databinding.ItemPopularBinding
import com.example.movieapp.databinding.ItemSearchBinding
import com.example.movieapp.utils.BANNER_COUNT
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import com.example.movieapp.utils.extensions.toTwoDecimals

import javax.inject.Inject

class SearchAdapter  @Inject constructor(): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    lateinit var binding: ItemSearchBinding


    private var bannerItem= emptyList<ResponseSimilar.Result>()


    inner class SearchViewHolder(item: View): RecyclerView.ViewHolder(item){


        fun onBind(items: ResponseSimilar.Result){



binding.txtTitle.text=items.title


            val vote=items.voteAverage!!.toTwoDecimals()
            binding.rateTxt.text=vote


            binding.appRating.rating=items.voteAverage.toFloat()/2






            val url="$TMDB_IMAGE_BASE_URL_W780${items.posterPath}"

binding.imgPoster.loadImage(url)



            binding.root.setOnClickListener {


                onItemClickListener?.let {
                    it(items)

                }



            }
        }


    }












    private var onItemClickListener:((ResponseSimilar.Result)->Unit)?=null

    fun setOnItemClickListener(listener:(ResponseSimilar.Result)->Unit){

        onItemClickListener=listener



    }




    fun setData(data:List<ResponseSimilar.Result>){

        val diffUtils=DiffUtils(bannerItem,data)
        val diff= DiffUtil.calculateDiff( diffUtils)

        bannerItem=data

        diff.dispatchUpdatesTo(this)





    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding= ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return           SearchViewHolder(binding.root)
    }


    override fun getItemViewType(position: Int)=position
    override fun getItemId(position: Int)=position.toLong()
    override fun getItemCount()=bannerItem.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(bannerItem[position])
    }





    class DiffUtils(private val oldItem:List<ResponseSimilar.Result>, private val newItem:List<ResponseSimilar.Result>):
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