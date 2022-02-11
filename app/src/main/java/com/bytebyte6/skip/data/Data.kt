package com.bytebyte6.skip.data

object Data {

    val sports = mutableListOf<Sport>()

    private val running = Sport(
        name = "跑步",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 0,
        maxCount = 0,
        trainingWay = TrainingWay.BY_TIME,
        maxGroup = 0,
        minGroup = 0,
        groupRestDuration = 0,
        restDuration = 60
    )

    private val deepSquat = Sport(
        name = "深蹲",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 10,
        maxCount = 30,
        trainingWay = TrainingWay.BY_GROUP,
        maxGroup = 10,
        minGroup = 3,
        groupRestDuration = 60,
        restDuration = 60
    )

    private val pushUp = Sport(
        name = "俯卧撑",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 10,
        maxCount = 30,
        trainingWay = TrainingWay.BY_GROUP,
        maxGroup = 10,
        minGroup = 3,
        groupRestDuration = 60,
        restDuration = 60
    )

    private val jumpJack = Sport(
        name = "开合跳",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 10,
        maxCount = 30,
        trainingWay = TrainingWay.BY_GROUP,
        maxGroup = 10,
        minGroup = 3,
        groupRestDuration = 60,
        restDuration = 60
    )

    private val oneHandedPushUps = Sport(
        name = "单手俯卧撑",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 10,
        maxCount = 30,
        trainingWay = TrainingWay.BY_GROUP,
        maxGroup = 10,
        minGroup = 3,
        groupRestDuration = 60,
        restDuration = 60
    )

    private val diamondPushUps = Sport(
        name = "钻石俯卧撑",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 10,
        maxCount = 30,
        trainingWay = TrainingWay.BY_GROUP,
        maxGroup = 10,
        minGroup = 3,
        groupRestDuration = 60,
        restDuration = 60
    )

    private val backHandPushUps = Sport(
        name = "后手俯卧撑",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 10,
        maxCount = 30,
        trainingWay = TrainingWay.BY_GROUP,
        maxGroup = 10,
        minGroup = 3,
        groupRestDuration = 60,
        restDuration = 60
    )

    private val pullUp = Sport(
        name = "引体向上",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 10,
        maxCount = 30,
        trainingWay = TrainingWay.BY_GROUP,
        maxGroup = 10,
        minGroup = 3,
        groupRestDuration = 60,
        restDuration = 60
    )

    private val muscleUp = Sport(
        name = "双立臂",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 10,
        maxCount = 30,
        trainingWay = TrainingWay.BY_GROUP,
        maxGroup = 10,
        minGroup = 3,
        groupRestDuration = 60,
        restDuration = 60
    )

    private val closeGripChinUp = Sport(
        name = "窄距引体向上",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 10,
        maxCount = 30,
        trainingWay = TrainingWay.BY_GROUP,
        maxGroup = 10,
        minGroup = 3,
        groupRestDuration = 60,
        restDuration = 60
    )

    private val widePullUps = Sport(
        name = "宽距引体向上",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 10,
        maxCount = 30,
        trainingWay = TrainingWay.BY_GROUP,
        maxGroup = 10,
        minGroup = 3,
        groupRestDuration = 60,
        restDuration = 60
    )

    private val ropeSkipping = Sport(
        name = "跳绳",
        minDuration = 60,
        maxDuration = 60 * 10,
        minCount = 100,
        maxCount = 3000,
        trainingWay = TrainingWay.BY_GROUP,
        maxGroup = 10,
        minGroup = 3,
        groupRestDuration = 60,
        restDuration = 60
    )

    init {
        sports.add(running)
        sports.add(deepSquat)
        sports.add(pushUp)
        sports.add(jumpJack)
        sports.add(oneHandedPushUps)
        sports.add(pullUp)
        sports.add(muscleUp)
        sports.add(closeGripChinUp)
        sports.add(widePullUps)
        sports.add(ropeSkipping)
        sports.add(diamondPushUps)
        sports.add(backHandPushUps)
    }
}