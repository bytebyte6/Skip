package com.bytebyte6.skip

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.bytebyte6.skip.data.Account
import com.bytebyte6.skip.data.AccountDao
import com.bytebyte6.skip.data.AppDataBase
import com.bytebyte6.skip.databinding.ActivityAccountBinding
import com.google.android.material.textfield.TextInputLayout
import java.util.concurrent.Executors

class AccountActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AccountActivity"
    }

    private lateinit var binding: ActivityAccountBinding
    private lateinit var accountDao: AccountDao
    private val cryptographyManager = CryptographyManager()
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

                    encryption(account, password)
                }
            }
    }

    private fun encryption(account: String, password: String) {
        val keyName = getString(R.string.secret_key_name)
        val encryptionCipher = cryptographyManager.getInitializedCipherForEncryption(keyName)
        val info = BiometricPromptUtils.createPromptInfo(this)
        val biometricPrompt = BiometricPromptUtils.createBiometricPrompt(this) { result ->
            result.cryptoObject?.cipher?.run {
                Log.d(TAG, "密码是: $password")
                val encryptedBytes = this.doFinal(password.toByteArray())
                val encryptedString = encryptedBytes?.decodeToString()
                Log.d(TAG, "加密后: $encryptedString")
                executorService.execute {
                    accountDao.insert(
                        Account(
                            account = account,
                            password = encryptedBytes,
                            iv = this.iv
                        )
                    )
                }
            }
        }
        biometricPrompt.authenticate(info, BiometricPrompt.CryptoObject(encryptionCipher))
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