package com.bossco.spacexclient.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bossco.spacexclient.models.Info
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

    fun saveInfo(info: Info){
        repository.saveInfo(info)
    }

    val infoMessage = repository.infoMessage

}