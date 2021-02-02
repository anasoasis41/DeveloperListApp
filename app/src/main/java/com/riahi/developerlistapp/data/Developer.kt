package com.riahi.developerlistapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "developers")
data class Developer (
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val picture: String,
    val hired: String
)