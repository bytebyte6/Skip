package com.bytebyte6.skip

import android.content.Context
import com.bytebyte6.skip.R

fun Context.getTimeString(second: Int): String {
    return if (second < 60) {
        getString(R.string.second_d, second)
    } else if (second >= 60 && second % 60 == 0) {
        getString(R.string.minute_d, second / 60)
    } else {
        getString(R.string.minute_second_d, second / 60, second % 60)
    }
}