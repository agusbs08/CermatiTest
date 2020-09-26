package com.tes.cermati.agusbudi.di.component

import android.app.Application
import com.tes.cermati.agusbudi.app.App
import com.tes.cermati.agusbudi.di.module.ActivityModule
import com.tes.cermati.agusbudi.di.module.AppModule
import com.tes.cermati.agusbudi.app.AppPreference
import com.tes.cermati.agusbudi.di.module.ContextModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ContextModule::class, AppModule::class, ActivityModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app : App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}