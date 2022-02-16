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

        val newSingleThreadExecutor = Executors.newSingleThreadExecutor()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BiometricPrompt.Builder(this)
                .setTitle("F")
                .setNegativeButton("F", newSingleThreadExecutor, { _, _ -> })
                .build()
                .authenticate(
                    CancellationSignal(),
                    newSingleThreadExecutor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                            super.onAuthenticationSucceeded(result)
                            Toast.makeText(
                                application,
                                "onAuthenticationSucceeded",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence?
                        ) {
                            super.onAuthenticationError(errorCode, errString)
                            Log.e("TAG", "onAuthenticationError: $errString $errorCode ")
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            Log.e("TAG", "onAuthenticationFailed: ")
                        }

                        override fun onAuthenticationHelp(
                            helpCode: Int,
                            helpString: CharSequence?
                        ) {
                            super.onAuthenticationHelp(helpCode, helpString)
                            Log.d("TAG", "onAuthenticationHelp: ")
                        }
                    })
        }
    }
}