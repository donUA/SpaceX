package com.bossco

import android.app.Application
import com.bossco.spacexclient.di.AppComponent
import com.bossco.spacexclient.di.AppModule
import com.bossco.spacexclient.di.DaggerAppComponent

/**
 * Created by Don Muthiani on 8/5/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
open class MyApplication : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(application = this)).build()

    }

    fun getDaggerComponent(): AppComponent {
        return appComponent
    }


}