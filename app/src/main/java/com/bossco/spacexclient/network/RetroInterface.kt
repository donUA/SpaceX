package com.bossco.spacexclient.network

import com.bossco.spacexclient.models.Info
import com.bossco.spacexclient.models.Launch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Don Muthiani on 8/5/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
interface RetroInterface {
    @GET("v3/info?filter=id,name,founder,founded,employees,launch_sites,valuation")
    fun getInfo(

    ): Call<Info>


    @GET("v3/launches?filter=_id,mission_name,launch_date_unix,launch_success,rocket,links")
    fun getLaunches(
        @Query("id") boolean: Boolean,
        @Query("start") start: String?,
        @Query("end") end: String?,
        @Query("order") order: String?,
    ): Call<List<Launch>>
}