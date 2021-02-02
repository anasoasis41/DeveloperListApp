package com.riahi.developerlistapp.data.service

import com.riahi.developerlistapp.data.model.DeveloperList
import retrofit2.Response
import retrofit2.http.GET

interface DeveloperService {
    @GET("/trombi")
    suspend fun getDeveloperData(): Response<DeveloperList>
}