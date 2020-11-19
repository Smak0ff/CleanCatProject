package com.example.cleancatproject


import com.example.cleancatproject.di.DaggerAppComponent
import com.example.cleancatproject.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : DaggerApplication(), HasAndroidInjector{
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun applicationInjector() = DaggerAppComponent.builder()
        .bindApp(this)
        .bindAppModules(AppModule(this))
        .build()

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}