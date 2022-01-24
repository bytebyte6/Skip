package com.bytebyte6.skip

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppEntity(
    @PrimaryKey
    val packageName: String,
    val count: Int,
    val isClickable:Boolean,
    val parentIsClickable:Boolean,
    val text:String
)