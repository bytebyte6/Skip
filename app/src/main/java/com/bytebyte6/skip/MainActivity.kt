package com.bytebyte6.skip

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.bytebyte6.skip.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val pongReceiver = PongReceiver()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerReceiver(pongReceiver, IntentFilter(SkipService.PONG))
        binding.button.setOnClickListener {
            goToSettingsScreen()
        }
        binding.toolbar.setOnMenuItemClickListener {
            startActivity(Intent(this,LogActivity::class.java))
            true
        }
        sendBroadcast(Intent(SkipService.PING))
    }

    private fun goToSettingsScreen() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onDestroy() {
        unregisterReceiver(pongReceiver)
        super.onDestroy()
    }

    inner class PongReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            binding.button.isEnabled = false
            binding.textView.setText(R.string.skip_service_enable)
        }
    }
}