package com.tes.cermati.agusbudi.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tes.cermati.agusbudi.di.key.ViewModelKey
import com.tes.cermati.agusbudi.util.factory.ViewModelFactory
import com.tes.cermati.agusbudi.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindDetailsViewModel(searchViewModel: SearchViewModel?): ViewModel?
}