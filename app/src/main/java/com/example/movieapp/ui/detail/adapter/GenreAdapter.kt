package com.example.movieapp.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.detail.ResponseDetail
import com.example.movieapp.databinding.ItemGenreBinding
import com.example.movieapp.utils.BANNER_COUNT
import javax.inject.Inject

class GenreAdapter @Inject constructor(): RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    lateinit var binding: ItemGenreBinding


    private var bannerItem= emptyList<ResponseDetail.Genre>()


    inner class GenreViewHolder(item: View): RecyclerView.ViewHolder(item){


        fun onBind(items: ResponseDetail.Genre){


binding.txtGenres.text=items.name






        }


    }















    fun setData(data:List<ResponseDetail.Genre>){

        val diffUtils=DiffUtils(bannerItem,data)
        val diff= DiffUtil.calculateDiff( diffUtils)

        bannerItem=data

        diff.dispatchUpdatesTo(this)





    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        binding= ItemGenreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return           GenreViewHolder(binding.root)
    }


    override fun getItemViewType(position: Int)=position
    override fun getItemId(position: Int)=position.toLong()
    override fun getItemCount()=bannerItem.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.onBind(bannerItem[position])
    }





    class DiffUtils(private val oldItem:List<ResponseDetail.Genre>, private val newItem:List<ResponseDetail.Genre>):
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