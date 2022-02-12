package com.bytebyte6.skip.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Log(
    @PrimaryKey
    val packageName: String,
    val count: Int,
    val isClickable: Boolean,
    val parentIsClickable: Boolean,
    val text: String
)