package com.riahi.developerlistapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.riahi.developerlistapp.data.model.Developer

@Dao
interface DeveloperDao {

    @Query("SELECT * from developers")
    fun getAll(): List<Developer>

    @Insert
    suspend fun insertDeveloper(developer: Developer)

    @Insert
    suspend fun insertDevelopers(developers: List<Developer>)

    @Query("DELETE from developers")
    suspend fun deleteAll()
}