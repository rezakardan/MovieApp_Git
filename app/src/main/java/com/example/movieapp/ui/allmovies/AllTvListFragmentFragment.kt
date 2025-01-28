package com.example.movieapp.ui.allmovies

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.FragmentAllTvListFragmentBinding
import com.example.movieapp.ui.allmovies.adapter.TvListAdapter
import com.example.movieapp.utils.network.NetworkChecker
import com.example.movieapp.viewmodel.AllTvListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class AllTvListFragmentFragment : Fragment() {
    lateinit var binding: FragmentAllTvListFragmentBinding


    private val viewModels by viewModels<AllTvListViewModel>()


    var isNetworkAvailable = false


    @Inject
    lateinit var networkChecker: NetworkChecker

    @Inject
    lateinit var tvListAdapter: TvListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllTvListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        tvListAdapter.setOnItemClickListener {

            val directions =
                AllTvListFragmentFragmentDirections.actionHomeFragmentToDetailMoviesFragment(it.id.toString())
            findNavController().navigate(directions)



            Log.d("Recycler", "ID: " +it.id)

        }






        binding.imgBack.setOnClickListener {

            findNavController().popBackStack()
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//
//
//            networkChecker.checkNetwork().collect {
//
//                isNetworkAvailable = it
//
//            }
//        }


        //   if (isNetworkAvailable) {

        viewLifecycleOwner.lifecycleScope.launch {

            viewModels.tvListLiveData.collectLatest { pagingData ->

                tvListAdapter.submitData(pagingData)


            }


        }
        binding.moviesList.adapter = tvListAdapter
        binding.moviesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewLifecycleOwner.lifecycleScope.launch {


            tvListAdapter.loadStateFlow.collect {


                val state = it.refresh

                binding.loading.isVisible = state is LoadState.Loading


            }


            //    binding.moviesList.adapter=tvListAdapter.withLoadStateFooter(LoadMoreAdapter{tvListAdapter.retry()})


        }


    }


}


//}