package com.bytebyte6.skip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bytebyte6.skip.databinding.ActivityTodayTrainingBinding

class TodayTrainingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodayTrainingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AddSportDialog(this).show()
    }
}