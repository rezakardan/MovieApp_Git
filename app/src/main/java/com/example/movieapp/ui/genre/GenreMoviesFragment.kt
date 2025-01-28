package com.example.movieapp.ui.genre

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.model.genre.ResponseGenreList
import com.example.movieapp.databinding.FragmentDetailMoviesBinding
import com.example.movieapp.databinding.FragmentGenreMoviesBinding
import com.example.movieapp.ui.allmovies.adapter.LoadMoreAdapter

import com.example.movieapp.utils.network.NetworkChecker
import com.example.movieapp.utils.network.NetworkRequest
import com.example.movieapp.viewmodel.DetailViewModel
import com.example.movieapp.viewmodel.GenreViewModel
import com.example.movieapp.viewmodel.MoviesByGenreViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class GenreMoviesFragment : Fragment() {
    lateinit var binding: FragmentGenreMoviesBinding

    private val viewModel by viewModels<GenreViewModel>()


    private val moviesByGenreViewModel by viewModels<MoviesByGenreViewModel>()


    @Inject
    lateinit var genreMoviesAdapter: GenreMoviesAdapter


    @Inject
    lateinit var networkChecker: NetworkChecker

    private var isNetworkAvailable = false


    var chipId = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenreMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }











    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        genreMoviesAdapter.setOnItemClickListener {


            val directions=GenreMoviesFragmentDirections.actionGenreMoviesFragmentToDetailMoviesFragment(it.id.toString())


            findNavController().navigate(directions)




        }




        binding.imgBack.setOnClickListener {


            findNavController().popBackStack(R.id.homeFragment, false)


        }








        lifecycleScope.launch {


            networkChecker.checkNetwork().collect {


                isNetworkAvailable = it


            }
        }


        if (isNetworkAvailable) {


            viewModel.callGenreApi()


        }




        loadGenreApi()


        loadMoviesByGenre()

        initRecyclerView()

        loadDataState()
    }

    private fun loadGenreApi() {

        viewModel.genreListLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {

                is NetworkRequest.Loading -> {
                    binding.loading.isVisible = true

                    binding.sortScroll.isVisible = false
                }


                is NetworkRequest.Success -> {
                    binding.loading.isVisible = false

                    binding.sortScroll.isVisible = true


                    response.data?.let {


                        initChipGroup(it.genres.toMutableList())


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


    private fun initChipGroup(list: MutableList<ResponseGenreList.Genre>) {
        // به طور مستقیم به tempList نسبت داده می‌شود

var tempList= mutableListOf<ResponseGenreList.Genre>()

tempList.clear()
         tempList = list

        // اضافه کردن Chip ها به ChipGroup
        tempList.forEach { genre ->
            val chip = Chip(requireContext()).apply {
                val drawable = ChipDrawable.createFromAttributes(
                    requireContext(),
                    null,
                    0,
                    R.style.FilterChipsBackground
                )
                setChipDrawable(drawable)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                text = genre.name
                id = genre.id // اختصاص id به chip
            }

            // اضافه کردن Chip به ChipGroup
            binding.sortChipGroup.addView(chip)
        }

        // اطمینان از انتخاب Chip اول به صورت پیش فرض
        if (binding.sortChipGroup.childCount > 0) {
            val firstChip = binding.sortChipGroup.getChildAt(0) as Chip
            binding.sortChipGroup.check(firstChip.id)  // انتخاب اولین chip
            // بارگذاری اطلاعات مربوط به اولین genre به طور پیش‌فرض
            moviesByGenreViewModel.updateMoviesByGenre(firstChip.id.toString())
        }

        // تنظیم onCheckedStateChangeListener تنها یکبار برای ChipGroup
        binding.sortChipGroup.setOnCheckedStateChangeListener { group, _ ->
            // دریافت لیست از chip های انتخاب شده
            val checkedChipIds = group.checkedChipIds

            // اگر هیچ chip انتخاب نشده باشد، هیچ کاری انجام نمی‌دهیم
            if (checkedChipIds.isEmpty()) return@setOnCheckedStateChangeListener

            // بررسی ایندکس هر Chip انتخاب شده و گرفتن اطلاعات مربوطه
            checkedChipIds.forEach { checkedChipId ->
                // پیدا کردن ایندکس بر اساس id
                val genre = tempList.find { it.id == checkedChipId }

                genre?.let {
                    // پاک کردن داده‌های قبلی
                    genreMoviesAdapter.submitData(
                        lifecycle,
                        PagingData.empty()
                    ) // پاک کردن داده‌های قبلی
                    // ارسال داده‌ها به viewModel برای بارگذاری لیست جدید
                    moviesByGenreViewModel.updateMoviesByGenre(it.id.toString())
                } ?: run {
                    Log.e("ChipGroup", "CheckedChipId is not valid or not found in the list")
                }
            }
        }
    }

    private fun loadMoviesByGenre() {
        moviesByGenreViewModel.moviesByGenre.observe(viewLifecycleOwner) { pagingData ->
            // پاک کردن داده‌های قبلی و ارسال داده‌های جدید به آداپتور
            genreMoviesAdapter.submitData(lifecycle, pagingData)
        }
    }

    private fun initRecyclerView() {
        binding.moviesList.adapter =
            genreMoviesAdapter.withLoadStateFooter(LoadMoreAdapter { genreMoviesAdapter.retry() })

        binding.moviesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        genreMoviesAdapter.setOnItemClickListener {
            val directions =
                GenreMoviesFragmentDirections.actionGenreMoviesFragmentToDetailMoviesFragment(it.id.toString())
            findNavController().navigate(directions)
        }
    }

    private fun loadDataState() {
        genreMoviesAdapter.addLoadStateListener { state ->
            binding.loading.isVisible = state.source.refresh is LoadState.Loading
            binding.moviesList.isVisible = state.source.refresh is LoadState.NotLoading
        }
    }
}
