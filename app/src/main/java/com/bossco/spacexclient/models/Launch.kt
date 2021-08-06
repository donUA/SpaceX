package com.bossco.spacexclient.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

/**
 * Created by Don Muthiani on 8/6/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
@Entity(tableName = "launch")
data class Launch(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val mission_name: String? = null,
    val launch_date_unix: Long? = null,
    val launch_success: Boolean? = null,
    @TypeConverters
    val rocket: List<Rocket>? = null,
    @TypeConverters
    var links: List<Links>? = null
)

data class Rocket(
    val rocket_name: String? = null,
    val rocket_type: String? = null,
)

data class Links(
    val mission_patch: String? = null,
    val mission_patch_small: String? = null,
    val article_link: String? = null,
    val wikipedia: String? = null,
    val video_link: String? = null
)