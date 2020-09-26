package com.tes.cermati.agusbudi.di.module

import com.tes.cermati.agusbudi.ui.MainActivity
import com.tes.cermati.agusbudi.ui.MainFragmentBindingModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [MainFragmentBindingModule::class])
    abstract fun bindMainActivity(): MainActivity?
}