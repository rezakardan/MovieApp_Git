package com.example.movieapp.ui.search

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.model.detail.ResponseSimilar
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.utils.extensions.showSnackBar
import com.example.movieapp.utils.network.NetworkChecker
import com.example.movieapp.utils.network.NetworkRequest
import com.example.movieapp.viewmodel.SearchViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding


    private var isNetworkAvailable = false


    @Inject
    lateinit var networkChecker: NetworkChecker


    private val viewmodel by viewModels<SearchViewmodel>()


    @Inject
    lateinit var searchAdapter: SearchAdapter


    private var searchTxt = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        lifecycleScope.launch {


            networkChecker.checkNetwork().collect {


                isNetworkAvailable = it


            }
        }



        if (isNetworkAvailable) {


            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {



                        searchTxt = newText!!


                        if (searchTxt.length > 2) {


                            viewmodel.searchByKeyword(searchTxt)


                        }



                    return true
                }


            })


        } else {
            binding.root.showSnackBar(getString(R.string.checkYourNetwork))
        }





        loadSearchData()
    }

    private fun loadSearchData() {
        viewmodel.searchByKeywordLiveData.observe(viewLifecycleOwner){response->



            when(response){



                is NetworkRequest.Loading->{
binding.emptyLay.isVisible=false
                }



                is NetworkRequest.Success->{


                    response.data?.results?.let {data->


                        if (data.isNotEmpty()){

                            binding.emptyLay.isVisible=false


                            binding.searchList.isVisible=true


                            searchAdapter.setData(data)


                            initSearchData(data)



                        }else{


                            binding.emptyLay.isVisible=true


                            binding.searchList.isVisible=false


                        }










                    }








                }


                is NetworkRequest.Error->{
                    Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT).show()
                }




            }










        }
    }


    private fun initSearchData(data:List<ResponseSimilar.Result>){

        searchAdapter.setData(data)

        binding.searchList.adapter=searchAdapter

        binding.searchList.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)




        searchAdapter.setOnItemClickListener {



            val directions=SearchFragmentDirections.actionSearchFragmentToDetailMoviesFragment(it.id.toString())

findNavController().navigate(directions)


        }



    }
}