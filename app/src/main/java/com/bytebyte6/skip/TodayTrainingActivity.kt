package com.bytebyte6.skip

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bytebyte6.skip.databinding.ActivityTodayTrainingBinding

class TodayTrainingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodayTrainingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.add_sport) {
                startActivity(Intent(this, AddSportActivity::class.java))
            } else {
                startActivity(Intent(this, SportListActivity::class.java))
            }
            true
        }
    }
}