package com.bossco.spacexclient.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bossco.spacexclient.models.Info
import com.bossco.spacexclient.models.Launch
import com.bossco.spacexclient.repositories.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Don Muthiani on 8/5/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
class AppViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {


    private var infoMutable = MutableLiveData<Info>()
    val infoLiveData: LiveData<Info>
        get() = infoMutable

    private var launchMutableData = MutableLiveData<List<Launch>>()
    val launchLiveData: LiveData<List<Launch>>
        get() = launchMutableData

    private var progressMutableLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean>
        get() = progressMutableLiveData

    private var messagesMutableLiveData = MutableLiveData<String>()
    val messagesLiveData: LiveData<String>
        get() = messagesMutableLiveData


    fun getInfo(){
        viewModelScope.launch {
            repository.getInfo(
                infoMutable,
                progressMutableLiveData,
                messagesMutableLiveData
            )
        }

    }

    fun getLaunchData(){
        viewModelScope.launch {
            repository.getLaunchData(
                launchMutableData,
                progressMutableLiveData,
                messagesMutableLiveData
            )
        }

    }

    fun saveInfo(info: Info){
        viewModelScope.launch {
            repository.saveInfo(info)
        }
    }

    fun saveLaunches(launch: List<Launch>){
        viewModelScope.launch {
            repository.saveLaunch(launch)
        }
    }

    val infoMessage = repository.infoMessage

    val launchMessage = repository.launchMessage



}