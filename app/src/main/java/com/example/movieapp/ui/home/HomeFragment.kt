package com.example.movieapp.ui.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.movieapp.R
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.data.model.home.ResponseTvOnTheAir
import com.example.movieapp.data.model.home.ResponseUpComing
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.ui.home.adapter.BannerAdapter
import com.example.movieapp.ui.home.adapter.PopularAdapter
import com.example.movieapp.ui.home.adapter.TvOnTheAirAdapter
import com.example.movieapp.ui.home.adapter.TvVideoAdapter
import com.example.movieapp.ui.home.adapter.UpComingAdapter
import com.example.movieapp.utils.Action
import com.example.movieapp.utils.Crime
import com.example.movieapp.utils.Drama
import com.example.movieapp.utils.ITEM_COUNT
import com.example.movieapp.utils.POPULAR
import com.example.movieapp.utils.TV
import com.example.movieapp.utils.TV_Movie
import com.example.movieapp.utils.TV_ON_THE_AIR
import com.example.movieapp.utils.UPCOMING
import com.example.movieapp.utils.VIDEO_COUNT
import com.example.movieapp.utils.extensions.setStatusBarIconsColor
import com.example.movieapp.utils.network.NetworkChecker
import com.example.movieapp.utils.network.NetworkRequest
import com.example.movieapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding


    private val viewmodel by viewModels<HomeViewModel>()


    private var isNetworkAvailable = false


    @Inject
    lateinit var networkChecker: NetworkChecker


    private var scrollIndex = 0

    @Inject
    lateinit var bannerAdapter: BannerAdapter


    @Inject
    lateinit var popularAdapter: PopularAdapter


    @Inject
    lateinit var tvVideoAdapter: TvVideoAdapter


    @Inject
    lateinit var tvOnTheAirAdapter: TvOnTheAirAdapter


    @Inject
    lateinit var upComingAdapter: UpComingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







        findNavController().popBackStack(R.id.splashFragment, true)


        lifecycleScope.launch {


            networkChecker.checkNetwork().collect {


                isNetworkAvailable = it


            }
        }

        if (isNetworkAvailable) {


            viewmodel.callTopRatedApi()

            viewmodel.callPopularApi()

            viewmodel.callTvListApi()
            viewmodel.callTvOnTheAirApi()

            viewmodel.callUpComingApi()


        }



















        loadTopRated()

        loadPopular()


        loadTvList()


        loadTvOnTheAir()


        loadUpComing()

    }


    private fun loadTopRated() {
        viewmodel.topRatedLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.loading.isVisible = true
                    binding.container.isVisible = false

                }


                is NetworkRequest.Success -> {
                    binding.loading.isVisible = false
                    binding.container.isVisible = true


                    response.data?.results?.let { data ->


                        if (data.isNotEmpty()) {


                            initTopRatedRecycler(data)


                        }


                    }


                }


                is NetworkRequest.Error -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT)
                        .show()
                }


            }


        }
    }

    private fun initTopRatedRecycler(data: List<ResponsePopular.Result>) {

        bannerAdapter.setData(data)
        autoScrollBannerList(data)

        binding.bannerList.apply {
            adapter = bannerAdapter
            binding.bannerList.adapter = adapter
            binding.bannerList.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        }
        bannerAdapter.setOnItemClickListener {

            val directions =
                HomeFragmentDirections.actionHomeFragmentToDetailMoviesFragment(it.id.toString())

            findNavController().navigate(directions)


        }




        binding.seeAllTopRated.setOnClickListener {


            findNavController().navigate(R.id.action_homeFragment_to_topRatedMoviesFragment)


        }


    }

    private fun autoScrollBannerList(data: List<ResponsePopular.Result>) {

        viewLifecycleOwner.lifecycleScope.launch {


            repeatOnLifecycle(Lifecycle.State.CREATED) {


                repeat(2000) {

                    delay(5000)








                    if (scrollIndex < data.size) {


                        scrollIndex += 1


                    } else {
                        scrollIndex = 0
                    }

                    binding.bannerList.smoothScrollToPosition(scrollIndex)

                }
            }
        }

    }


    private fun loadPopular() {
        viewmodel.popularLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.loading.isVisible = true
                    binding.container.isVisible = false

                }


                is NetworkRequest.Success -> {
                    binding.loading.isVisible = false
                    binding.container.isVisible = true


                    response.data?.results?.let { data ->


                        if (data.isNotEmpty()) {


                            ITEM_COUNT = if (data.size < VIDEO_COUNT) {


                                data.size
                            } else {

                                VIDEO_COUNT

                            }


                            initPopularList(data)


                        }


                    }


                }


                is NetworkRequest.Error -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT)
                        .show()
                }


            }


        }
    }

    private fun initPopularList(data: List<ResponsePopular.Result>) {

        popularAdapter.setData(data)




        binding.popularList.adapter = popularAdapter
        binding.popularList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )



        popularAdapter.setOnItemClickListener {

            val directions =
                HomeFragmentDirections.actionHomeFragmentToDetailMoviesFragment(it.id.toString())

            findNavController().navigate(directions)


        }

        binding.seeAllPop.setOnClickListener {


            findNavController().navigate(R.id.action_homeFragment_to_allPopularMoviesFragment)

        }

    }


    private fun loadTvList() {
        viewmodel.tvListLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.loading.isVisible = true
                    binding.container.isVisible = false

                }


                is NetworkRequest.Success -> {
                    binding.loading.isVisible = false
                    binding.container.isVisible = true


                    response.data?.results?.let { data ->


                        if (data.isNotEmpty()) {


                            ITEM_COUNT = if (data.size < VIDEO_COUNT) {


                                data.size
                            } else {

                                VIDEO_COUNT

                            }


                            initTvListList(data)


                        }


                    }


                }


                is NetworkRequest.Error -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT)
                        .show()
                }


            }


        }
    }

    private fun initTvListList(data: List<ResponseMovies.Result>) {

        tvVideoAdapter.setData(data)




        binding.tvList.adapter = tvVideoAdapter
        binding.tvList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        tvVideoAdapter.setOnItemClickListener {

            val directions =
                HomeFragmentDirections.actionHomeFragmentToDetailMoviesFragment(it.id.toString())

            findNavController().navigate(directions)


        }




        binding.seeAllTv.setOnClickListener {


            findNavController().navigate(R.id.action_homeFragment_to_allTvListFragmentFragment)

        }


    }


    private fun loadTvOnTheAir() {
        viewmodel.tvOnTheAirLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.loading.isVisible = true
                    binding.container.isVisible = false

                }


                is NetworkRequest.Success -> {
                    binding.loading.isVisible = false
                    binding.container.isVisible = true


                    response.data?.results?.let { data ->


                        if (data.isNotEmpty()) {


                            ITEM_COUNT = if (data.size < VIDEO_COUNT) {


                                data.size
                            } else {

                                VIDEO_COUNT

                            }


                            initTvOnTheAirList(data)


                        }


                    }


                }


                is NetworkRequest.Error -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT)
                        .show()
                }


            }


        }
    }

    private fun initTvOnTheAirList(data: List<ResponseTvOnTheAir.Result>) {

        tvOnTheAirAdapter.setData(data)




        binding.tvAirList.adapter = tvOnTheAirAdapter
        binding.tvAirList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        tvOnTheAirAdapter.setOnItemClickListener {

            val directions =
                HomeFragmentDirections.actionHomeFragmentToDetailMoviesFragment(it.id.toString())

            findNavController().navigate(directions)


        }




        binding.seeAllAir.setOnClickListener {


            findNavController().navigate(R.id.action_homeFragment_to_allTvOnAirFragment)

        }


    }


    private fun loadUpComing() {
        viewmodel.upComingLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.loading.isVisible = true
                    binding.container.isVisible = false

                }


                is NetworkRequest.Success -> {
                    binding.loading.isVisible = false
                    binding.container.isVisible = true


                    response.data?.results?.let { data ->


                        if (data.isNotEmpty()) {


                            ITEM_COUNT = if (data.size < VIDEO_COUNT) {


                                data.size
                            } else {

                                VIDEO_COUNT

                            }



                            initUpComingList(data)


                        }


                    }


                }


                is NetworkRequest.Error -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT)
                        .show()
                }


            }


        }
    }

    private fun initUpComingList(data: List<ResponseUpComing.Result>) {

        upComingAdapter.setData(data)




        binding.UpComingList.adapter = upComingAdapter
        binding.UpComingList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        upComingAdapter.setOnItemClickListener {

            val directions =
                HomeFragmentDirections.actionHomeFragmentToDetailMoviesFragment(it.id.toString())

            findNavController().navigate(directions)


        }



        binding.seeAllUpcoming.setOnClickListener {


            findNavController().navigate(R.id.action_homeFragment_to_allUpComingFragment)

        }


    }

}

