package com.bytebyte6.skip.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import com.bytebyte6.skip.*
import com.bytebyte6.skip.data.Account
import com.bytebyte6.skip.data.AppDataBase
import com.bytebyte6.skip.databinding.FragmentAccountBinding
import com.google.android.material.textfield.TextInputLayout
import java.util.concurrent.Executors

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    private val cryptographyManager = CryptographyManager()

    private val binding get() = _binding!!

    private val executorService = Executors.newSingleThreadExecutor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            val canAuthenticate = BiometricManager
                .from(requireContext())
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
            if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
                showDialog()
            } else {
                Toast.makeText(requireContext(), getString(R.string.tips_not_add_password_and_fingerprint), Toast.LENGTH_SHORT)
                    .show()
            }
            true
        }
    }
    private fun showDialog() {
        var etAccountLayout: TextInputLayout?
        var etPasswordLayout: TextInputLayout?
        AlertDialog.Builder(requireContext())
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
        val info = BiometricPromptUtils.createPromptInfo(requireActivity())
        val biometricPrompt = BiometricPromptUtils.createBiometricPrompt(requireActivity()) { result ->
            result.cryptoObject?.cipher?.run {
                Log.d(AccountActivity.TAG, "密码是: $password")
                val encryptedBytes = this.doFinal(password.toByteArray())
                val encryptedString = encryptedBytes?.decodeToString()
                Log.d(AccountActivity.TAG, "加密后: $encryptedString")
                executorService.execute {
                    AppDataBase.getAppDataBase(requireContext()).accountDao().insert(
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
        AppDataBase.getAppDataBase(requireContext()).accountDao().run {
            list().observe(viewLifecycleOwner) {
                accountAdapter.update(it)
            }
        }
    }
}