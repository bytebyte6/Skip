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
    var name: String = "",
    /**
     * 最小持续时间 s
     */
    var minDuration: Int = -1,
    /**
     * 最大持续时间 s
     */
    var maxDuration: Int = -1,
    /**
     * 0 按时间
     * 1 按组数
     */
    var trainingWay: Int = TrainingWay.BY_TIME,
    /**
     * 最小次数
     */
    var minCount: Int = -1,
    /**
     * 最小组数
     */
    var minGroup: Int = -1,
    /**
     * 最大次数
     */
    var maxCount: Int = -1,
    /**
     * 最大组数
     */
    var maxGroup: Int = -1,
    /**
     * 组间休息时间 s
     */
    var groupRestDuration: Int = -1,
    /**
     * 完成这个项目之后的休息时间 s
     */
    var restDuration: Int = -1,
    /**
     * 是否达标
     */
    var goal: Boolean = false
)