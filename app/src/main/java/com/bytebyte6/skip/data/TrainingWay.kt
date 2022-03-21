package com.bytebyte6.skip.data

import com.bytebyte6.skip.data.TrainingWay.BY_TIME
import com.bytebyte6.skip.data.TrainingWay.BY_GROUP

object TrainingWay {
    const val BY_TIME = 0
    const val BY_GROUP = 1
}

fun Int.byTime() = this == BY_TIME

fun Int.byGroup() = this == BY_GROUP