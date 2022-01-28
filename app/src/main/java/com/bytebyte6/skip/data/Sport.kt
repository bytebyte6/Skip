package com.bytebyte6.skip.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sport(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val duration: Int,
    val restDuration: Int
)