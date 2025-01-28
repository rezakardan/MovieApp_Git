package com.example.movieapp.ui.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.movieapp.R
import com.example.movieapp.data.db.FavoriteEntity
import com.example.movieapp.data.model.detail.ResponseDetail
import com.example.movieapp.databinding.FragmentDetailMoviesBinding
import com.example.movieapp.ui.detail.adapter.ActorAdapter
import com.example.movieapp.ui.detail.adapter.GenreAdapter
import com.example.movieapp.ui.detail.adapter.SimilarAdapter
import com.example.movieapp.utils.ITEM_COUNT
import com.example.movieapp.utils.SIMILAR_COUNT
import com.example.movieapp.utils.TMDB_IMAGE_BASE_URL_W780

import com.example.movieapp.utils.extensions.toHoursAndMinutesString
import com.example.movieapp.utils.extensions.toTwoDecimals
import com.example.movieapp.utils.network.NetworkChecker
import com.example.movieapp.utils.network.NetworkRequest
import com.example.movieapp.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailMoviesFragment : Fragment() {
    lateinit var binding: FragmentDetailMoviesBinding


    private val viewModel by viewModels<DetailViewModel>()


    private val args by navArgs<DetailMoviesFragmentArgs>()


    @Inject
    lateinit var genreAdapter: GenreAdapter


    @Inject
    lateinit var actorAdapter: ActorAdapter


    @Inject
    lateinit var similarAdapter: SimilarAdapter

    @Inject
    lateinit var networkChecker: NetworkChecker

    private var isNetworkAvailable = false


    @Inject
    lateinit var favoriteEntity: FavoriteEntity

    private var isExistInDatabase = false


    private var isInDatabse:String="0"

    var itemId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch {


            networkChecker.checkNetwork().collect {


                isNetworkAvailable = it


            }
        }

        args.let {

            Log.d("Click", "ID: " + it.id)

            itemId = it.id.toInt()


            isInDatabse=it.id

        }



        if (isNetworkAvailable) {


            viewModel.callMovieDetailsApi(itemId)

            viewModel.callPopularActorApi(itemId)


            viewModel.callAllActorsApi(itemId)



            viewModel.callSimilarMoviesApi(itemId)










            viewModel.isInDatabaseOrNo(isInDatabse)

        }









        binding.imgBack.setOnClickListener {


            findNavController().popBackStack(R.id.homeFragment, false)


        }






















        binding.imgPlayer.setOnClickListener {


            val directions =
                DetailMoviesFragmentDirections.actionDetailMoviesFragmentToVideoPlayerFragment(
                    itemId.toString()
                )

            findNavController().navigate(directions)

        }











        loadDetails()
        loadAllActors()
        loadSimilar()





        similarAdapter.setOnItemClickListener {

            val directions=DetailMoviesFragmentDirections.actionHomeFragmentToDetailMoviesFragment(it.id.toString())
            findNavController().navigate(directions)






        }
    }




    @SuppressLint("SuspiciousIndentation")
    private fun loadDetails() {
        viewModel.movieDetailsLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                }


                is NetworkRequest.Success -> {


                    response.data?.let { data ->


                        clickOnBookMark(data)



                        binding.txtTitle.text = data.title

                        Log.d("name", "ID: " + data.title)


                        val url = "$TMDB_IMAGE_BASE_URL_W780${data.posterPath}"
                        binding.imgPoster.load(url)


                        binding.txtTimeDate.text =
                            "${data.releaseDate}  -  ${data.runtime.toHoursAndMinutesString()}"


                        val vote = data.voteAverage!!.toTwoDecimals()


                        binding.txtRating.text = vote



                        binding.txtOverview.text = data.overview

                        genreAdapter.setData(data.genres!!)
                        binding.GenreList.adapter = genreAdapter

                        binding.GenreList.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )


                    }


                }


                is NetworkRequest.Error -> {

                    Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT)
                        .show()
                }


            }


        }
    }





    private fun loadAllActors() {
        viewModel.getAllActorsLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                }


                is NetworkRequest.Success -> {


                    response.data?.let { data ->


                        actorAdapter.setData(data.cast)
                        binding.actorList.adapter = actorAdapter

                        binding.actorList.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )


                    }


                }


                is NetworkRequest.Error -> {

                    Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT)
                        .show()
                }


            }


        }
    }
















    @SuppressLint("SuspiciousIndentation")
    private fun loadSimilar() {
        viewModel.similarMoviesLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                }


                is NetworkRequest.Success -> {


                    response.data?.let { data ->


                        if (data.results!!.isNotEmpty()) {
                            ITEM_COUNT =
                                if (data.results.size < SIMILAR_COUNT) {
                                    data.results.size
                                } else {
                                    SIMILAR_COUNT
                                }



                            similarAdapter.setData(data.results)
                            binding.similarList.adapter = similarAdapter

                            binding.similarList.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )


                        }

                    }
                }


                is NetworkRequest.Error -> {

                    Toast.makeText(
                        requireContext(),
                        response.error.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()


                }
            }


        }
    }


    private fun clickOnBookMark(data: ResponseDetail) {


        viewModel.isInDatabaseOrNo(data.id.toString())

        checkExistInDatabase()

        binding.imgBookmark.setOnClickListener {


            if (isExistInDatabase) {


                deleteFromDatabase(data)


            } else {

                addToDatabase(data)
            }


        }


    }


    private fun checkExistInDatabase() {
        viewModel.isProductInDatabaseLiveData.observe(viewLifecycleOwner) {


            isExistInDatabase = it


            if (isExistInDatabase) {


                binding.imgBookmark.apply {


                    setColorFilter(ContextCompat.getColor(requireContext(), R.color.Gold))

                }


            }


        }
    }


    private fun deleteFromDatabase(data: ResponseDetail) {

        favoriteEntity.image = data.posterPath
        favoriteEntity.title = data.title

        favoriteEntity.id = data.id.toString()


        val vote = data.voteAverage!!.toTwoDecimals()

        favoriteEntity.rating = vote

        favoriteEntity.date = data.releaseDate





        viewModel.deleteFavorite(favoriteEntity)






        binding.imgBookmark.apply {


            setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray))


        }
    }


    private fun addToDatabase(data: ResponseDetail) {


        favoriteEntity.image = data.posterPath
        favoriteEntity.title = data.title

        favoriteEntity.id = data.id.toString()


        val vote = data.voteAverage!!.toTwoDecimals()

        favoriteEntity.rating = vote

        favoriteEntity.date = data.releaseDate


        viewModel.addToFavorite(favoriteEntity)




        binding.imgBookmark.apply {


            setColorFilter(ContextCompat.getColor(requireContext(), R.color.Gold))


        }
    }


}

