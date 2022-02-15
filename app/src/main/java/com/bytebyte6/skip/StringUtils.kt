package com.bytebyte6.skip

import java.lang.NumberFormatException

fun CharSequence?.toInt(): Int {
    return if (this == null) {
        0
    } else {
        try {
            Integer.parseInt(this.toString())
        } catch (e: NumberFormatException) {
            0
        }
    }
}

fun CharSequence?.toStringOrEmpty(): String {
    return this?.toString() ?: ""
}