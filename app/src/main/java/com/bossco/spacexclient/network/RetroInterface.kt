package com.bossco.spacexclient.network

import com.bossco.spacexclient.models.Info
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Don Muthiani on 8/5/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
interface RetroInterface {
    @GET("3/info")
    fun getInfo(): Call<Info>

}