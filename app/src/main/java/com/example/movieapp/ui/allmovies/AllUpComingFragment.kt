package com.example.movieapp.ui.allmovies

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.movieapp.databinding.FragmentAllUpComingBinding
import com.example.movieapp.ui.allmovies.adapter.AllUpComingAdapter
import com.example.movieapp.utils.network.NetworkChecker
import com.example.movieapp.viewmodel.AllUpComingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class AllUpComingFragment : Fragment() {
    lateinit var binding: FragmentAllUpComingBinding


    private val viewModels by viewModels<AllUpComingViewModel>()


    var isNetworkAvailable = false


    @Inject
    lateinit var networkChecker: NetworkChecker

    @Inject
    lateinit var allUpComingAdapter: AllUpComingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllUpComingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        allUpComingAdapter.setOnItemClickListener {


           val directions=AllUpComingFragmentDirections.actionHomeFragmentToDetailMoviesFragment(it.id.toString())

           findNavController().navigate(directions)





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



       // if (isNetworkAvailable) {

            viewLifecycleOwner.lifecycleScope.launch {

                viewModels.tvListLiveData.collectLatest {

                    allUpComingAdapter.submitData(it)


                }

















            }
        binding.moviesList.adapter = allUpComingAdapter
        binding.moviesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            viewLifecycleOwner.lifecycleScope.launch {


                allUpComingAdapter.loadStateFlow.collect{






                    val state=it.refresh

                    binding.loading.isVisible=state is LoadState.Loading






                }





             //   binding.moviesList.adapter=allUpComingAdapter.withLoadStateFooter(LoadMoreAdapter{allUpComingAdapter.retry()})



            }







        }


    }


//}









