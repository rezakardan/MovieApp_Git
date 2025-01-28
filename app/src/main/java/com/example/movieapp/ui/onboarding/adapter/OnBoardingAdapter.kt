package com.example.movieapp.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.onboarding.IntroSlide
import com.example.movieapp.databinding.ItemOnboardingBinding
import com.example.movieapp.utils.BANNER_COUNT
import javax.inject.Inject

class OnBoardingAdapter  @Inject constructor(): RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    lateinit var binding: ItemOnboardingBinding


    private var bannerItem= emptyList<IntroSlide>()


    inner class OnBoardingViewHolder(item: View): RecyclerView.ViewHolder(item){


        fun onBind(items: IntroSlide){

binding.animation.setAnimation(items.iconResourceId)

binding.txtTitle.text=items.title
            binding.txtDesc.text=items.description




        }


    }

















    fun setData(data:List<IntroSlide>){

        val diffUtils=DiffUtils(bannerItem,data)
        val diff= DiffUtil.calculateDiff( diffUtils)

        bannerItem=data

        diff.dispatchUpdatesTo(this)





    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        binding= ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return           OnBoardingViewHolder(binding.root)
    }


    override fun getItemViewType(position: Int)=position
    override fun getItemId(position: Int)=position.toLong()
    override fun getItemCount()=bannerItem.size

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.onBind(bannerItem[position])
    }





    class DiffUtils(private val oldItem:List<IntroSlide>, private val newItem:List<IntroSlide>):
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