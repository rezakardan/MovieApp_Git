package com.example.movieapp.ui.allmovies

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.example.movieapp.databinding.FragmentAllPopularMoviesBinding
import com.example.movieapp.databinding.FragmentTopRatedMoviesBinding
import com.example.movieapp.ui.allmovies.adapter.LoadMoreAdapter
import com.example.movieapp.ui.allmovies.adapter.TopRatedAdapter
import com.example.movieapp.utils.network.NetworkChecker
import com.example.movieapp.viewmodel.AllMoviesViewModel
import com.example.movieapp.viewmodel.AllPopularViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllPopularMoviesFragment : Fragment() {
    lateinit var binding: FragmentAllPopularMoviesBinding


    private val viewModels by viewModels<AllPopularViewModel>()


    var isNetworkAvailable = false


    @Inject
    lateinit var networkChecker: NetworkChecker

    @Inject
    lateinit var topRatedAdapter: TopRatedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





      requireActivity().  window.apply {


            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            statusBarColor = Color.TRANSPARENT



            WindowCompat.getInsetsController(this,this.decorView).apply {

                isAppearanceLightStatusBars=true



            }

        }






        binding.imgBack.setOnClickListener {

            findNavController().popBackStack()
        }


//        viewLifecycleOwner.lifecycleScope.launch {
//
//            networkChecker.checkNetwork().collect {
//
//                isNetworkAvailable = it
//
//
//            }


            topRatedAdapter.setOnItemClickListener {


                val directions =
                    AllPopularMoviesFragmentDirections.actionAllPopularMoviesFragmentToDetailMoviesFragment(
                        it.id.toString()
                    )
                findNavController().navigate(directions)


            }


         //   if (isNetworkAvailable) {


                viewLifecycleOwner.lifecycleScope.launch {
                    // Collecting PagingData from the ViewModel
                    viewModels.allPopular.collectLatest { pagingData ->
                        // Ensure the data is of the correct type
                        Log.e("test", "Received PagingData: ${pagingData::class.java}")

                        // Submit the PagingData to the adapter
                        topRatedAdapter.submitData(pagingData)
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
  //  }
//}