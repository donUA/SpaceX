package com.bossco.spacexclient.repositories

import androidx.lifecycle.MutableLiveData
import com.bossco.spacexclient.database.AppDao
import com.bossco.spacexclient.models.Info
import com.bossco.spacexclient.network.RetroInterface
import kotlinx.coroutines.withTimeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Don Muthiani on 8/5/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
class AppRepository @Inject constructor(
    private val appDao: AppDao,
    private val retroInterface: RetroInterface
) {

    suspend fun getInfo(
        infoMutableLiveData: MutableLiveData<Info>,
        progressMutableLiveData: MutableLiveData<Boolean>,
        messagesMutableLiveData: MutableLiveData<String>
    ) {
        try {
            progressMutableLiveData.postValue(true)
            withTimeout(3_000) {
                retroInterface.getInfo().enqueue(object : Callback<Info> {
                    override fun onResponse(call: Call<Info>, response: Response<Info>) {
                        progressMutableLiveData.postValue(false)
                        if (response.isSuccessful) {
                            infoMutableLiveData.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<Info>, t: Throwable) {
                        infoMutableLiveData.postValue(null)
                        progressMutableLiveData.postValue(false)
                        messagesMutableLiveData.postValue("Error connecting to SpaceX, check internet connection.")
                    }

                })
            }
        } catch (e: Exception) {
            messagesMutableLiveData.postValue("Error connecting to SpaceX")
        }
    }

    val infoMessage = appDao.getInFo()

    fun saveInfo(info: Info) {
        appDao.insertInfo(info)

    }

}