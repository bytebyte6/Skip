package com.bytebyte6.skip

import android.graphics.Color
import androidx.appcompat.app.AppCompatDelegate
import kotlin.random.Random


fun getImage(): Int {
    return when (Random.nextInt(8)) {
        0 -> R.drawable.ic_baseline_all_inclusive_24
        1 -> R.drawable.ic_baseline_amp_stories_24
        2 -> R.drawable.ic_baseline_apartment_24
        3 -> R.drawable.ic_baseline_apps_24
        4 -> R.drawable.ic_baseline_blur_on_24
        5 -> R.drawable.ic_baseline_bubble_chart_24
        6 -> R.drawable.ic_baseline_color_lens_24
        7 -> R.drawable.ic_baseline_favorite_24
        else -> R.drawable.ic_baseline_widgets_24
    }
}

fun randomColorByNightMode(): Int {
    val dark = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    val from = if (dark) 0 else 200
    val until = if (dark) 100 else 256
    val a = 255/* Random.Default.nextInt(from, until)*/
    val r = Random.Default.nextInt(from, until)
    val g = Random.Default.nextInt(from, until)
    val b = Random.Default.nextInt(from, until)
    return Color.argb(a, r, g, b)
}

fun randomColor(): Int {
    val from = 0
    val until = 256
    val a = Random.Default.nextInt(from, until)
    val r = Random.Default.nextInt(from, until)
    val g = Random.Default.nextInt(from, until)
    val b = Random.Default.nextInt(from, until)
    return Color.argb(a, r, g, b)
}