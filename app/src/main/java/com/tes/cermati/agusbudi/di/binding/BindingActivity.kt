package com.tes.cermati.agusbudi.di.binding

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.tes.cermati.agusbudi.base.BaseActivity
import com.tes.cermati.agusbudi.util.factory.ViewModelFactory
import javax.inject.Inject

abstract class BindingActivity<B : ViewDataBinding, VM : ViewModel> : BaseActivity<B>(){

    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var viewModel: VM

    fun createViedModel(cls : Class<VM>) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(cls)
    }

    abstract fun observableViewModel()
}