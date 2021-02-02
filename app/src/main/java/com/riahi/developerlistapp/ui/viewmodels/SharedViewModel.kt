package com.riahi.developerlistapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.riahi.developerlistapp.data.model.Developer
import com.riahi.developerlistapp.data.repositories.DeveloperRepository

class SharedViewModel (val app: Application) : AndroidViewModel(app) {

    private val dataRepository =
        DeveloperRepository(app)
    val developerData = dataRepository.developerData

    val selectedDev = MutableLiveData<Developer>()
    val activityTitle = MutableLiveData<String>()

    fun refreshData() {
        dataRepository.refreshDataFromWeb()
    }
}