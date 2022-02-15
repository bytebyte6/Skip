package com.bytebyte6.skip.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Log(
    @PrimaryKey
    val packageName: String,
    val count: Int,
    val isClickable: Boolean,
    val parentIsClickable: Boolean,
    val text: String,
    val className:String,
    val nodeSize:Int,

) {
    override fun toString(): String {
        return "Log(\n" +
                "   packageName='$packageName'\n" +
                "   count=$count\n" +
                "   isClickable=$isClickable\n" +
                "   parentIsClickable=$parentIsClickable\n" +
                "   text='$text'\n" +
                "className='$className'\n" +
                "   nodeSize=$nodeSize" +
                "\n)"
    }
}