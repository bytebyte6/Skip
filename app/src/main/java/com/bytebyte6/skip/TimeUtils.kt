package com.bytebyte6.skip

import android.content.Context
import java.util.*

fun Context.getTimeString(second: Int): String {
    return if (second < 60) {
        getString(R.string.second_d, second)
    } else if (second >= 60 && second % 60 == 0) {
        getString(R.string.minute_d, second / 60)
    } else {
        getString(R.string.minute_second_d, second / 60, second % 60)
    }
}

fun getsTheZeroTimeStamp(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.MINUTE,0)
    calendar.set(Calendar.SECOND,0)
    calendar.set(Calendar.HOUR_OF_DAY,0)
    calendar.set(Calendar.MILLISECOND,0)
    return calendar.timeInMillis
}