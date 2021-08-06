package com.bossco.spacexclient.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bossco.spacexclient.models.Info
import com.bossco.spacexclient.models.Launch

/**
 * Created by Don Muthiani on 8/5/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(info: Info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLunches(launch: List<Launch>)

    @Query("SELECT * FROM INFO")
    fun getInFo(): LiveData<Info>

    @Query("SELECT * FROM launch")
    fun getLaunch(): LiveData<List<Launch>>
    
    @Query("DELETE FROM launch")
    suspend fun deleteLaunch()

    @Query("DELETE FROM info")
    suspend fun deleteInfo()
}