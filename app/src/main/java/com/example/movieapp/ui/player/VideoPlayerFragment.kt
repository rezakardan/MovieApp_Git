package com.example.movieapp.ui.player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentVideoPlayerBinding
import com.example.movieapp.utils.extensions.showSnackBar
import com.example.movieapp.utils.network.NetworkChecker
import com.example.movieapp.utils.network.NetworkRequest
import com.example.movieapp.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class VideoPlayerFragment : Fragment() {

    lateinit var binding: FragmentVideoPlayerBinding


    private val args by navArgs<VideoPlayerFragmentArgs>()


    private val viewModel by viewModels<DetailViewModel>()


    @Inject
    lateinit var networkChecker: NetworkChecker

    private var isNetworkAvailable = false

    private var itemId = 0

    private lateinit var youTubePlayer: YouTubePlayer

    private var isFullScreen = false

    private var onBackPressedCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

            if (isFullScreen) {


                youTubePlayer.toggleFullscreen()


            } else {


                val directions =
                    VideoPlayerFragmentDirections.actionVideoPlayerFragmentToDetailMoviesFragment(
                        itemId.toString()
                    )

                findNavController().navigate(directions)


            }
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallBack)




        viewLifecycleOwner.lifecycleScope.launch {


            networkChecker.checkNetwork().collect {


                isNetworkAvailable = it


            }
        }

        args.let {


            if (it.id.isNotEmpty()) {


                itemId = it.id.toInt()
                viewLifecycleOwner.lifecycleScope.launch {


                    repeatOnLifecycle(Lifecycle.State.CREATED) {


                        if (isNetworkAvailable) {

                            viewModel.callPlayVideoApi(itemId)
                        } else {
                            binding.root.showSnackBar(getString(R.string.checkYourNetwork))
                        }


                    }


                }


            }


        }




        loadVideoPlayer()


    }

    @SuppressLint("SuspiciousIndentation")
    private fun loadVideoPlayer() {



        viewModel.playVideoLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                }


                is NetworkRequest.Success -> {


                    response.data?.results?.firstOrNull()?.key?.let { firstVideoId ->

                        with(binding) {
                            val iFramePlayerOptions =
                                IFramePlayerOptions.Builder()
                                    .controls(1)
                                    .fullscreen(1) // enable full screen button
                                    .autoplay(1)
                                    .build()
                      binding.youtuberPlayerView .enableAutomaticInitialization = false
                            binding.   youtuberPlayerView.initialize(
                                object : AbstractYouTubePlayerListener() {
                                    override fun onReady(youTubePlayer: YouTubePlayer) {
                                        this@VideoPlayerFragment.youTubePlayer = youTubePlayer
                                        youTubePlayer.loadVideo(firstVideoId, 0f)
                                    }
                                },
                                iFramePlayerOptions,
                            )

                            lifecycle.addObserver(youtuberPlayerView)
                        }




                    }
                }

                is NetworkRequest.Error -> {
                    Snackbar.make(binding.root, response.error.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }


        }


    }


    override fun onStart() {
        super.onStart()


        requireActivity().requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE



    }

    override fun onStop() {
        super.onStop()

        requireActivity().requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }


}