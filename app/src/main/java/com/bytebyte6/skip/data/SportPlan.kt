package com.bytebyte6.skip.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SportPlan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    /**
     * 当天日期
     */
    val date: Long,
    /**
     * 运动总时长
     */
    val duration: Int,
    /**
     * 菜单
     */
    val egg: Boolean = false,
    /**
     * 运动项目列表
     */
    val list: List<Sport> = emptyList(),
    /**
     * 是否达标
     */
    val goal: Boolean = false
)