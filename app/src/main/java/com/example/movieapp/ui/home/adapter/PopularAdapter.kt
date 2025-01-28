package com.example.movieapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.databinding.ItemBannerBinding
import com.example.movieapp.databinding.ItemPopularBinding
import com.example.movieapp.utils.BANNER_COUNT
import com.example.movieapp.utils.TMDB_CAST_IMAGE_BASE_URL_W342
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import com.example.movieapp.utils.extensions.toTwoDecimals
import javax.inject.Inject

class PopularAdapter @Inject constructor(): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    lateinit var binding: ItemPopularBinding


    private var bannerItem= emptyList<ResponsePopular.Result>()


    inner class PopularViewHolder(item: View): RecyclerView.ViewHolder(item){


        fun onBind(items: ResponsePopular.Result){

            binding.  txtTitle.text = items.title


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












    private var onItemClickListener:((ResponsePopular.Result)->Unit)?=null

    fun setOnItemClickListener(listener:(ResponsePopular.Result)->Unit){

        onItemClickListener=listener



    }




    fun setData(data:List<ResponsePopular.Result>){

        val diffUtils=DiffUtils(bannerItem,data)
        val diff= DiffUtil.calculateDiff( diffUtils)

        bannerItem=data

        diff.dispatchUpdatesTo(this)





    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        binding= ItemPopularBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return           PopularViewHolder(binding.root)
    }


    override fun getItemViewType(position: Int)=position
    override fun getItemId(position: Int)=position.toLong()
    override fun getItemCount()=if (bannerItem.size> BANNER_COUNT) BANNER_COUNT else bannerItem.size

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.onBind(bannerItem[position])
    }





    class DiffUtils(private val oldItem:List<ResponsePopular.Result>, private val newItem:List<ResponsePopular.Result>):
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