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
    fun listToRocket(value: List<Rocket>?): String? = Gson().toJson(value)

    @TypeConverter
    fun jsonToRocket(value: String) = Gson().fromJson(value, Array<Rocket>::class.java).toList()

    @TypeConverter
    fun listToLinks(value: List<Links>?): String? = Gson().toJson(value)

    @TypeConverter
    fun jsonToLinks(value: String) = Gson().fromJson(value, Array<Links>::class.java).toList()






}