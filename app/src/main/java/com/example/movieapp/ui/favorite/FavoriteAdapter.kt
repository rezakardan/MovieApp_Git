package com.example.movieapp.ui.favorite

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.db.FavoriteEntity
import com.example.movieapp.databinding.ItemFavoriteBinding
import com.example.movieapp.utils.ITEM_COUNT
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780
import com.example.movieapp.utils.extensions.loadImage
import javax.inject.Inject

class FavoriteAdapter @Inject constructor(): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    lateinit var binding: ItemFavoriteBinding


    private var bannerItem= emptyList<FavoriteEntity>()


    inner class FavoriteViewHolder(item: View): RecyclerView.ViewHolder(item) {


        fun onBind(items: FavoriteEntity) {

            binding.apply {
                items.let {
                    // text
                    txtTitle.text = it.title
                  //  val vote = it.voteAverage!!.toTwoDecimals()



                    txtTimeDate.text=it.date


                    txtRating.text = it.rating
                    val moviePosterURL =    "${TMDB_IMAGE_BASE_URL_W780}${it.image}"





                    Log.e("image","url ${it.image}")

                    imgPoster.loadImage(moviePosterURL)
                }

                // click
                root.setOnClickListener {
                    // Click
                    onItemClickListener?.let { it(items) }
                }







            }
        }
    }


    private var onItemClickListener:((FavoriteEntity)->Unit)?=null

    fun setOnItemClickListener(listener:(FavoriteEntity)->Unit){

        onItemClickListener=listener



    }













    fun setData(data:List<FavoriteEntity>){

        val diffUtils=DiffUtils(bannerItem,data)
        val diff= DiffUtil.calculateDiff( diffUtils)

        bannerItem=data

        diff.dispatchUpdatesTo(this)





    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        binding= ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return           FavoriteViewHolder(binding.root)
    }


    override fun getItemViewType(position: Int)=position
    override fun getItemId(position: Int)=position.toLong()
    override fun getItemCount()= bannerItem.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.onBind(bannerItem[position])
    }





    class DiffUtils(private val oldItem:List<FavoriteEntity>, private val newItem:List<FavoriteEntity>):
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