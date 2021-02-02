package com.riahi.developerlistapp.data

import retrofit2.Response
import retrofit2.http.GET

interface DeveloperService {
    @GET("/trombi")
    suspend fun getDeveloperData(): Response<DeveloperList>
}