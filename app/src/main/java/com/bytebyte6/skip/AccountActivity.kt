package com.bytebyte6.skip

import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bytebyte6.skip.data.AccountDao
import com.bytebyte6.skip.data.AppDataBase
import com.bytebyte6.skip.databinding.ActivityAccountBinding
import com.google.android.material.textfield.TextInputLayout
import java.nio.charset.Charset
import java.util.concurrent.Executors

class AccountActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AccountActivity"
    }

    private lateinit var binding: ActivityAccountBinding
    private lateinit var accountDao: AccountDao
    private val executorService = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initRecyclerView()
        accountDao = AppDataBase.getAppDataBase(this).accountDao()
    }

    private fun initToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            showDialog()
            true
        }
    }

    private fun showDialog() {
        var etAccountLayout: TextInputLayout?
        var etPasswordLayout: TextInputLayout?
        AlertDialog.Builder(this)
            .setTitle(R.string.add_account)
            .setView(R.layout.view_edit_text)
            .setCancelable(false)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.enter, null)
            .create()
            .apply {
                show()
                etAccountLayout = findViewById(R.id.etAccountLayout)
                etPasswordLayout = findViewById(R.id.etPasswordLayout)
                getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    val account = etAccountLayout?.editText?.text?.toString() ?: ""
                    val password = etPasswordLayout?.editText?.text?.toString() ?: ""
                    if (account.isEmpty()) {
                        Toast.makeText(context, R.string.please_input_account, Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                    if (password.isEmpty()) {
                        Toast.makeText(context, R.string.please_input_password, Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                    dismiss()

                    fingerprintVerification(account,password)
                }
            }
    }

    private fun fingerprintVerification(account: String, password: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BiometricPrompt.Builder(this)
                .setTitle("验证指纹")
                .setDescription("使用指纹加解密账户信息")
                .setNegativeButton(getString(R.string.cancel), executorService, { _, _ -> })
                .build()
                .authenticate(
                    CancellationSignal(),
                    executorService,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                            super.onAuthenticationSucceeded(result)
                            Log.d(TAG, "onAuthenticationSucceeded: ")
                            result?.run {
                                Log.d(TAG, "密码是: $password")
                                val encryptedBytes = cryptoObject.cipher.doFinal(password.toByteArray())
                                val encryptedString = encryptedBytes.toString(Charset.defaultCharset())
                                Log.d(TAG, "加密后: $encryptedString")
                                val decryptedBytes = cryptoObject.cipher.doFinal(encryptedString.toByteArray())
                                val decryptedString = decryptedBytes.toString(Charset.defaultCharset())
                                Log.d(TAG, "解密后: $decryptedString")
                            }
//                            executorService.execute {
//                                accountDao.insert(Account(account = account, password = result.cryptoObject.cipher.provider.))
//                            }
                        }

                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence?
                        ) {
                            super.onAuthenticationError(errorCode, errString)
                            Log.e(TAG, "onAuthenticationError: $errString $errorCode ")
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            Log.e(TAG, "onAuthenticationFailed: ")
                        }

                        override fun onAuthenticationHelp(
                            helpCode: Int,
                            helpString: CharSequence?
                        ) {
                            super.onAuthenticationHelp(helpCode, helpString)
                            Log.d(TAG, "onAuthenticationHelp: ")
                        }
                    })
        }
    }

    private fun initRecyclerView() {
        val accountAdapter = AccountAdapter()
        binding.recyclerView.adapter = accountAdapter
        AppDataBase.getAppDataBase(this).accountDao().run {
            list().observe(this@AccountActivity, {
                accountAdapter.update(it)
            })
        }
    }
}