package com.bytebyte6.skip.data

data class RealSport(
    /**
     * 运动名称
     */
    var name: String = "",
    /**
     * 实际持续时间 s
     */
    var duration: Int = -1,
    /**
     * 0 按时间
     * 1 按组数
     */
    var trainingWay: Int = TrainingWay.BY_TIME,
    /**
     * 实际次数
     */
    var count: Int = -1,
    /**
     * 实际组数
     */
    var group: Int = -1,
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