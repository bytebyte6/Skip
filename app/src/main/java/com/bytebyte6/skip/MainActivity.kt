package com.bytebyte6.skip

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import com.bytebyte6.skip.data.AppDataBase
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
            when (it.itemId) {
                R.id.account -> startActivity(Intent(this, AccountActivity::class.java))
                R.id.log -> startActivity(Intent(this, LogActivity::class.java))
            }
            true
        }
        sendBroadcast(Intent(SkipService.PING))
        decryption()
    }

    private fun decryption() {
        val canAuthenticate = BiometricManager
            .from(applicationContext)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            AppDataBase.getAppDataBase(this).accountDao().list().observe(this,{
                if (!it.isNullOrEmpty()){
                    val cryptographyManager = CryptographyManager()
                    val keyName = getString(R.string.secret_key_name)
                    val encryptedBytes: ByteArray = it[0].password
                    val decryptionCipher = cryptographyManager.getInitializedCipherForDecryption(keyName, it[0].iv)
                    val info = BiometricPromptUtils.createPromptInfo(this)
                    val biometricPrompt = BiometricPromptUtils.createBiometricPrompt(this) { result ->
                        result.cryptoObject?.cipher?.run {
                            val decryptedString = this.doFinal(encryptedBytes).decodeToString()
                            Log.d(AccountActivity.TAG, "解密后: $decryptedString")
                        }
                    }
                    biometricPrompt.authenticate(info, BiometricPrompt.CryptoObject(decryptionCipher))
                }
            })
        } else {
            Toast.makeText(this, getString(R.string.tips_not_add_password_and_fingerprint), Toast.LENGTH_SHORT)
                .show()
        }
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