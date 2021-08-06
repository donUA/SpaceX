package com.bossco.spacexclient.repositories

import androidx.lifecycle.MutableLiveData
import com.bossco.spacexclient.database.AppDao
import com.bossco.spacexclient.models.Info
import com.bossco.spacexclient.models.Launch
import com.bossco.spacexclient.network.RetroInterface
import kotlinx.coroutines.withTimeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
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
//                        progressMutableLiveData.postValue(false)
                        if (response.isSuccessful) {
                            if(response.body()==null){
                                infoMutableLiveData.postValue(null)
                            }else {
                                infoMutableLiveData.postValue(response.body())
                            }
                        }
                    }

                    override fun onFailure(call: Call<Info>, t: Throwable) {
                        infoMutableLiveData.postValue(null)
//                        progressMutableLiveData.postValue(false)
                        messagesMutableLiveData.postValue("Error connecting to SpaceX, check internet connection.")
                    }

                })
            }
        } catch (e: Exception) {
            messagesMutableLiveData.postValue("Error connecting to SpaceX")
        }
    }


    suspend fun getLaunchData(
        launchMutableData: MutableLiveData<List<Launch>>,
        progressMutableLiveData: MutableLiveData<Boolean>,
        messagesMutableLiveData: MutableLiveData<String>,
        start: String?,
        end: String?,
        order: String?
    ) {
        try {
            progressMutableLiveData.postValue(true)
            withTimeout(3_000) {
                Timber.i("Fetching launches")
                retroInterface.getLaunches(
                    true,start,end,order
                ).enqueue(object : Callback<List<Launch>> {
                    override fun onResponse(call: Call<List<Launch>>, response: Response<List<Launch>>) {
                        progressMutableLiveData.postValue(false)
                        if (response.isSuccessful) {
                            if(response.body().isNullOrEmpty()){
                                launchMutableData.postValue(null)
                            }else {
                                launchMutableData.postValue(response.body())
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Launch>>, t: Throwable) {
                        Timber.i("onFailure ${t.message}")
                        launchMutableData.postValue(null)
                        progressMutableLiveData.postValue(false)
                        messagesMutableLiveData.postValue("Error connecting to SpaceX, check internet ${t.message}")
                    }

                })
            }
        } catch (e: Exception) {
            Timber.i("Error fetching launches $e")
            messagesMutableLiveData.postValue("Error connecting to SpaceX")
        }
    }

    val infoMessage = appDao.getInFo()

    val launchMessage = appDao.getLaunch()

    suspend fun saveInfo(info: Info) {
        appDao.insertInfo(info)
    }

    suspend fun saveLaunch(launch: List<Launch>) {
        appDao.insertLunches(launch)
    }

    suspend fun deleteLaunches() {
        appDao.deleteLaunch()
    }

}