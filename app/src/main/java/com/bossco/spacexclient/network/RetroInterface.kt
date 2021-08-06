package com.bossco.spacexclient.network

import com.bossco.spacexclient.models.Info
import com.bossco.spacexclient.models.Launch
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Don Muthiani on 8/5/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
interface RetroInterface {
    @GET("v3/info")
    fun getInfo(): Call<Info>


    @GET("v3/launches")
    fun getLaunches(): Call<Launch>
}