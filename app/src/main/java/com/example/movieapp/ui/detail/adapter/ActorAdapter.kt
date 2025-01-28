package com.example.movieapp.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.detail.ResponseActor
import com.example.movieapp.data.model.detail.ResponseAllActors
import com.example.movieapp.databinding.ItemActorBinding
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W500
import com.example.movieapp.utils.extensions.loadImage
import javax.inject.Inject

class ActorAdapter @Inject constructor(): RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    lateinit var binding: ItemActorBinding


    private var bannerItem= emptyList<ResponseAllActors.Cast>()


    inner class ActorViewHolder(item: View): RecyclerView.ViewHolder(item) {


        fun onBind(items: ResponseAllActors.Cast) {

            binding.apply {
                items.let {
                    // text
                    txtTitle.text = it.name
                    val moviePosterURL = "$TMDB_IMAGE_BASE_URL_W500${it.profilePath}"
                    itemImg.loadImage(moviePosterURL)
                }
            }
        }


    }













    fun setData(data:List<ResponseAllActors.Cast>){

        val diffUtils=DiffUtils(bannerItem,data)
        val diff= DiffUtil.calculateDiff( diffUtils)

        bannerItem=data

        diff.dispatchUpdatesTo(this)





    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        binding= ItemActorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return           ActorViewHolder(binding.root)
    }


    override fun getItemViewType(position: Int)=position
    override fun getItemId(position: Int)=position.toLong()
    override fun getItemCount()= bannerItem.size

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(bannerItem[position])
    }





    class DiffUtils(private val oldItem:List<ResponseAllActors.Cast>, private val newItem:List<ResponseAllActors.Cast>):
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