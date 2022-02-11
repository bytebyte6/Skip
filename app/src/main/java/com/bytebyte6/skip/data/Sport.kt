package com.bytebyte6.skip.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sport(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    /**
     * 运动名称
     */
    val name: String,
    /**
     * 最小持续时间 s
     */
    val minDuration: Int,
    /**
     * 最大持续时间 s
     */
    val maxDuration: Int,
    /**
     * 0 按时间
     * 1 按组数
     */
    val trainingWay:Int,
    /**
     * 最小次数
     */
    val minCount: Int,
    /**
     * 最小组数
     */
    val minGroup: Int,
    /**
     * 最大次数
     */
    val maxCount: Int,
    /**
     * 最大组数
     */
    val maxGroup: Int,
    /**
     * 组间休息时间 s
     */
    val groupRestDuration:Int,
    /**
     * 完成这个项目之后的休息时间 s
     */
    val restDuration: Int,
    /**
     * 是否达标
     */
    val goal:Boolean=false
)