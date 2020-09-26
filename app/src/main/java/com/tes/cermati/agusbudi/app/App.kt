package com.tes.cermati.agusbudi.app

import com.tes.cermati.agusbudi.di.component.AppComponent
import com.tes.cermati.agusbudi.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component: AppComponent = DaggerAppComponent.builder().application(this).build()
        component.inject(this)
        return component
    }

}