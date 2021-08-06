package com.bossco.spacexclient.database

import androidx.room.TypeConverter
import com.bossco.spacexclient.models.Links
import com.bossco.spacexclient.models.Rocket
import com.google.gson.Gson
import java.util.*

/**
 * Created by Don Muthiani on 8/6/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
class Converters {

    @TypeConverter
    fun rocketToString(value: Rocket): String = Gson().toJson(value)

    @TypeConverter
    fun stringToRocket(string: String): Rocket = Gson().fromJson(string, Rocket::class.java)


    @TypeConverter
    fun linksToString(value: Links?): String? = Gson().toJson(value)

    @TypeConverter
    fun stringToLinks(value: String) = Gson().fromJson(value, Links::class.java)






}