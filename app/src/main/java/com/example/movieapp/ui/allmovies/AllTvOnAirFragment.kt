package com.example.movieapp.ui.allmovies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.FragmentAllTvOnAirBinding
import com.example.movieapp.ui.allmovies.adapter.TvOnTheAirAdapter
import com.example.movieapp.utils.network.NetworkChecker
import com.example.movieapp.viewmodel.AllTvOnTheAirViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint

class AllTvOnAirFragment : Fragment() {
    lateinit var binding: FragmentAllTvOnAirBinding
    private val viewModels by viewModels<AllTvOnTheAirViewModel>()


    var isNetworkAvailable = false


    @Inject
    lateinit var networkChecker: NetworkChecker

@Inject
    lateinit var adapter: TvOnTheAirAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllTvOnAirBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setOnItemClickListener {

            val directions=AllTvOnAirFragmentDirections.actionHomeFragmentToDetailMoviesFragment(it.id.toString())
            findNavController().navigate(directions)



          //  Log.d("RecyclerClick", "ID: " +it.id)



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


            //  if (isNetworkAvailable) {

            viewLifecycleOwner.lifecycleScope.launch {

                viewModels.allTvOnTheAirLiveData.collectLatest { pagingData ->

                    adapter.submitData(pagingData)


                }








            }
            binding.moviesList.adapter = adapter
            binding.moviesList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            viewLifecycleOwner.lifecycleScope.launch {


                adapter.loadStateFlow.collect {


                    val state = it.refresh

                    binding.loading.isVisible = state is LoadState.Loading


                }


                //   binding.moviesList.adapter=tvListAdapter.withLoadStateFooter(LoadMoreAdapter{tvListAdapter.retry()})


            }


        }


    }


//}





