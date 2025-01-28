package com.example.movieapp.utils.di

import androidx.fragment.app.Fragment
import com.example.movieapp.ui.video.adapter.ExploreAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {


    @Provides
    fun provideVideoFragmentLifeCycle(fragment: Fragment):ExploreAdapter{


        return ExploreAdapter(fragment.lifecycle)

    }











}