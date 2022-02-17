package com.bytebyte6.skip

import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bytebyte6.skip.databinding.ActivityTodayTrainingBinding
import java.util.concurrent.Executors

class TodayTrainingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodayTrainingBinding

    private val viewModel by viewModels<TodayTrainingViewModel>()

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

        val realSportAdapter = RealSportAdapter()
        binding.recyclerView.adapter = realSportAdapter

        viewModel.sportPlanUI.observe(this, {
            if (it != null) {
                realSportAdapter.update(it.list)
            }
        })
    }
}