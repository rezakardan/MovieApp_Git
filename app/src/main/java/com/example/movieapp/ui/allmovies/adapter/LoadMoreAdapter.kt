package com.example.movieapp.ui.allmovies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.LoadMoreBinding

class LoadMoreAdapter(private val retry:()->Unit):LoadStateAdapter<LoadMoreAdapter.LoadMoreViewHolder> (){

    lateinit var binding:LoadMoreBinding







    inner class LoadMoreViewHolder(item:View,retry:()->Unit):RecyclerView.ViewHolder(item){




        init {


            binding.loadMoreRetry.setOnClickListener {


                retry()



            }


        }


        fun setData(state:LoadState){

            binding.loadMoreProgress.isVisible=state is LoadState.Loading

            binding.loadMoreRetry.isVisible=state is LoadState.Error




        }




    }

    override fun onBindViewHolder(holder: LoadMoreViewHolder, loadState: LoadState) {

holder.setData(loadState)




    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadMoreViewHolder {
        binding=LoadMoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadMoreViewHolder(binding.root,retry)
    }
}