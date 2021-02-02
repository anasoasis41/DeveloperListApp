package com.riahi.developerlistapp.data.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.riahi.developerlistapp.WEB_SERVICE_URL
import com.riahi.developerlistapp.data.service.DeveloperService
import com.riahi.developerlistapp.data.database.MyDatabase
import com.riahi.developerlistapp.data.model.Developer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class DeveloperRepository(val app: Application) {

    val developerData = MutableLiveData<List<Developer>>()
    private val developerDao = MyDatabase.getDatabase(
        app
    ).developerDao()

    init {
        // Background Thread
        CoroutineScope(Dispatchers.IO).launch {
            // Get all the data from database
            val data = developerDao.getAll()
            if (data.isEmpty()) {
                callWebService()
            } else {
                developerData.postValue(data)
                // Foreground Thread
                withContext(Dispatchers.Main) {
                    Toast.makeText(app, "Using local data", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Using remote data", Toast.LENGTH_LONG).show()
            }
            Timber.i("Calling web service")
            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val service = retrofit.create(DeveloperService::class.java)
            val serviceData = service.getDeveloperData().body()?.list ?: emptyList()
            developerData.postValue(serviceData)
            // Save data in room database
            developerDao.deleteAll()
            developerDao.insertDevelopers(serviceData)
        }
    }

    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    fun refreshDataFromWeb() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }
}