package com.example.movieapp.ui.favorite

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentFavoriteBinding
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.ui.favorite.FavoriteFragmentDirections
import com.example.movieapp.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding


    private val viewModel by viewModels<FavoriteViewModel>()


    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.getAllData()


binding.imgBack.setOnClickListener { findNavController().popBackStack() }

        favoriteAdapter.setOnItemClickListener { favoriteEntity->



val directions=FavoriteFragmentDirections.actionFavoriteFragmentToDetailMoviesFragment(favoriteEntity.id)

            findNavController().navigate(directions)







        }



        loadData()
    }

    private fun loadData() {
        viewModel.loadAllDataLiveData.observe(viewLifecycleOwner) {


            if (it.isNotEmpty()) {


                favoriteAdapter.setData(it)

                binding.favoriteList.adapter = favoriteAdapter

                binding.favoriteList.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)



            }


        }
    }
}