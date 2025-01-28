package com.example.movieapp.ui.allmovies

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.databinding.FragmentTopRatedMoviesBinding
import com.example.movieapp.ui.allmovies.adapter.LoadMoreAdapter
import com.example.movieapp.ui.allmovies.adapter.TopRatedAdapter
import com.example.movieapp.utils.network.NetworkChecker
import com.example.movieapp.viewmodel.AllMoviesViewModel
import com.example.movieapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint

class TopRatedMoviesFragment : Fragment() {


    lateinit var binding: FragmentTopRatedMoviesBinding


    private val viewModels by viewModels<AllMoviesViewModel>()


    var isNetworkAvailable = false


    @Inject
    lateinit var networkChecker: NetworkChecker

    @Inject
    lateinit var topRatedAdapter: TopRatedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopRatedMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.imgBack.setOnClickListener {

            findNavController().popBackStack()
        }



        topRatedAdapter.setOnItemClickListener {


            val directions =
                TopRatedMoviesFragmentDirections.actionTopRatedMoviesFragmentToDetailMoviesFragment(
                    it.id.toString()
                )

            findNavController().navigate(directions)


        }







//        viewLifecycleOwner.lifecycleScope.launch {
//
//            networkChecker.checkNetwork().collect {
//
//                isNetworkAvailable = it
//
//
//            }


          //  if (isNetworkAvailable) {


                viewLifecycleOwner.lifecycleScope.launch {


                    viewModels.allTopRatedMoviesLiveData.collectLatest {


                        topRatedAdapter.submitData(it)


                    }




                }

        binding.moviesList.adapter = topRatedAdapter

        binding.moviesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                viewLifecycleOwner.lifecycleScope.launch {
                    topRatedAdapter.loadStateFlow.collect {

                        val state = it.refresh

                        binding.loading.isVisible = state is LoadState.Loading


                    }





//                    binding.moviesList.adapter =
//                        topRatedAdapter.withLoadStateFooter(LoadMoreAdapter { topRatedAdapter.retry() })

                }
            }

        }
   // }
//}