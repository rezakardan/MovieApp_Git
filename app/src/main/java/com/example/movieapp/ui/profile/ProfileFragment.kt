package com.example.movieapp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentProfileBinding
import com.example.movieapp.utils.ACTOR_NAME
import com.example.movieapp.utils.ACTOR_WOMEN
import com.example.movieapp.utils.extensions.loadImageCircularProgress
import com.example.movieapp.utils.extensions.showSnackBar
import com.example.movieapp.utils.network.NetworkChecker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding




    private var isNetworkAvailable = false


    @Inject
    lateinit var networkChecker: NetworkChecker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (isNetworkAvailable) {
                imgActor.loadImageCircularProgress(ACTOR_WOMEN)
                txtActor.text = ACTOR_NAME
            } else {
                root.showSnackBar(getString(R.string.checkYourNetwork))
            }
        }
    }
}