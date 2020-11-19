package com.example.cleancatproject.di

import android.app.Application
import com.example.cleancatproject.App
import com.example.cleancatproject.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        FragmentModule::class
    ]
)

interface AppComponent : AndroidInjector<App> {

    override fun inject(instance: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindApp(app: Application): Builder

        fun bindAppModules(appModule: AppModule): Builder

        fun build(): AppComponent

    }

}