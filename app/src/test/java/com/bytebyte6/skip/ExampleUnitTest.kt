package com.bytebyte6.skip

import com.bytebyte6.skip.data.Data
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun test() {
        println(5f)
        println(5.0f)
        Data.sports.forEach {
            println(Data.getRealSport(it))
        }
    }
}