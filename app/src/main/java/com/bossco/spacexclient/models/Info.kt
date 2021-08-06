package com.bossco.spacexclient.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Don Muthiani on 8/5/21.
 * Copyright (c) 2021 Accuret. All rights reserved.
 */
@Entity(tableName = "info")
class Info(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String? = null,
    var founder: String? = null,
    var founded: Int? = null,
    var employees: Int? = null,
    var launch_sites: Int? = null,
    var valuation: String? = null
)