package com.bytebyte6.skip.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SportPlan(
    @PrimaryKey
    val id: Int = 0,
    val list: List<Sport>
)