package com.riahi.developerlistapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class DeveloperList(
    val list: List<Developer>
)

@Entity(tableName = "developers")
data class Developer (
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val picture: String,
    val hired: String
) {
    val imageUrl
        get() = picture
}