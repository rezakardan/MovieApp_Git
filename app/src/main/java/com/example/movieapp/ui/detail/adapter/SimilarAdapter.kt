package com.example.movieapp.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.detail.ResponseActor
import com.example.movieapp.data.model.detail.ResponseSimilar
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.databinding.ItemActorBinding
import com.example.movieapp.databinding.ItemPopularBinding
import com.example.movieapp.utils.ITEM_COUNT
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W500
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import com.example.movieapp.utils.extensions.toTwoDecimals
import javax.inject.Inject

class SimilarAdapter@Inject constructor(): RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder>() {

    lateinit var binding: ItemPopularBinding


    private var bannerItem= emptyList<ResponseSimilar.Result>()


    inner class SimilarViewHolder(item: View): RecyclerView.ViewHolder(item) {


        fun onBind(items: ResponseSimilar.Result) {

            binding.apply {
                items.let {
                    // text
                    txtTitle.text = it.title
                    val vote = it.voteAverage!!.toTwoDecimals()
                    txtRating.text = vote
                    val moviePosterURL = "$TMDB_IMAGE_BASE_URL_W780${it.posterPath}"
                    itemImg.loadImage(moviePosterURL)
                }

                // click
                root.setOnClickListener {
                    // Click
                    onItemClickListener?.let { it(items) }
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



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        binding= ItemPopularBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return           SimilarViewHolder(binding.root)
    }


    override fun getItemViewType(position: Int)=position
    override fun getItemId(position: Int)=position.toLong()
    override fun getItemCount()= ITEM_COUNT

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
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