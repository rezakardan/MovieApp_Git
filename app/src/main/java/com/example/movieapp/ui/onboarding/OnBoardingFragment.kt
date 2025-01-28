package com.example.movieapp.ui.onboarding

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.data.model.onboarding.dataLocal
import com.example.movieapp.data.stored.OnboardingPreferenceManager
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.databinding.FragmentOnBoardingBinding
import com.example.movieapp.ui.onboarding.adapter.OnBoardingAdapter
import com.example.movieapp.utils.network.NetworkChecker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class OnBoardingFragment : Fragment() {


    lateinit var binding: FragmentOnBoardingBinding





    @Inject
    lateinit var onBoardingAdapter: OnBoardingAdapter


    @Inject
    lateinit var onboardingPreference: OnboardingPreferenceManager

    // other
    private lateinit var indicatorsContainer: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {
            onBoardingAdapter.setData(dataLocal)
            onboardingViewPager.adapter = onBoardingAdapter
            onboardingViewPager.registerOnPageChangeCallback(
                object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        setCurrentIndicator(position)
                    }
                },
            )
        }
        (binding.onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        // click
        binding.apply {
            // next
            imgNext.setOnClickListener {
                if (onboardingViewPager.currentItem + 1 < onBoardingAdapter.itemCount) {
                    onboardingViewPager.currentItem += 1
                } else {
                    navigateToHome()
                }
            }
            // skip
            txtSkip.setOnClickListener {
                navigateToHome()
            }
            // start
            btnStarted.setOnClickListener {
                navigateToHome()
            }
        }
        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setupIndicators() {
        binding.apply {
            indicatorsContainer = indicatorContainers
            val indicators = arrayOfNulls<ImageView>(onBoardingAdapter.itemCount)
            val layoutParams: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            layoutParams.setMargins(8, 0, 8, 0)
            for (i in indicators.indices) {
                indicators[i] = ImageView(requireContext())
                indicators[i]?.let {
                    it.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.indicator_inactive_background,
                        ),
                    )
                    it.layoutParams = layoutParams
                    indicatorContainers.addView(it)
                }
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_active_background,
                    ),
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive_background,
                    ),
                )
            }
        }
    }

    private fun navigateToHome() {
        lifecycleScope.launch {
            onboardingPreference.saveOnboardingPreference(true)

                  findNavController().popBackStack(R.id.onBoardingFragment,true)


            findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
        }
    }
}