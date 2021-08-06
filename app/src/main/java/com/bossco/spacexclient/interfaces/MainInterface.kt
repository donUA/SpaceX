package com.bossco.spacexclient.interfaces

import com.bossco.spacexclient.models.Launch

/**
 * Created by Don Muthiani on 8/6/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
interface MainInterface {

    fun openArticle(launch: Launch){}
    fun openWikipedia(launch: Launch){}
    fun openVideo(launch: Launch){}
}