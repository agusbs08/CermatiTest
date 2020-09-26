package com.tes.cermati.agusbudi.ui

import com.tes.cermati.agusbudi.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun provideSearchFragment(): SearchFragment?
}