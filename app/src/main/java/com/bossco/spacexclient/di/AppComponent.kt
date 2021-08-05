package com.bossco.spacexclient.di

import com.bossco.spacexclient.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Don Muthiani on 8/5/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}