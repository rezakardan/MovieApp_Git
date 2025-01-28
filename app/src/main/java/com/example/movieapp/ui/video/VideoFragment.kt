package com.example.movieapp.ui.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.model.movie.moviesDataList
import com.example.movieapp.databinding.FragmentVideoBinding
import com.example.movieapp.ui.video.adapter.ExploreAdapter
import com.example.movieapp.utils.extensions.showSnackBar
import com.example.movieapp.utils.network.NetworkChecker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class VideoFragment : Fragment() {
    lateinit var binding: FragmentVideoBinding


    private var isNetworkAvailable = false


    @Inject
    lateinit var networkChecker: NetworkChecker


    @Inject
    lateinit var exploreAdapter: ExploreAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {


            networkChecker.checkNetwork().collect {

                isNetworkAvailable = it


            }


        }


        if (isNetworkAvailable) {

            viewLifecycleOwner.lifecycleScope.launch {


                repeatOnLifecycle(Lifecycle.State.CREATED) {

                    binding.videoList.visibility = View.VISIBLE


                    delay(3000)

                    exploreAdapter.setData(moviesDataList)

                    binding.videoList.adapter = exploreAdapter

                    binding.videoList.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


                }


            }


        } else {


            binding.root.showSnackBar(getString(R.string.checkYourNetwork))
        }


    }

}