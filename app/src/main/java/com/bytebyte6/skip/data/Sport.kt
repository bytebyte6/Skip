package com.bytebyte6.skip.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sport(
    @PrimaryKey
    val id: Int = 0,
    /**
     * 运动名称
     */
    val name: String,
    /**
     * 秒
     */
    val duration: Int,
    /**
     * 0 按时间
     * 1 按组数
     */
    val trainingWay:Int,
    /**
     * 次数
     */
    val count: Int,
    /**
     * 组数
     */
    val group: Int,
    /**
     * 组间休息时间
     */
    val groupRestDuration:Int,
    /**
     * 完成这个项目之后的休息时间
     */
    val restDuration: Int
)