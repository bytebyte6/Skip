package com.bytebyte6.skip.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bytebyte6.skip.BiometricPromptUtils
import com.bytebyte6.skip.CryptographyManager
import com.bytebyte6.skip.R
import com.bytebyte6.skip.data.Account
import com.bytebyte6.skip.databinding.ItemAccountBinding

class AccountAdapter(
    private val activity: FragmentActivity,
    private val cryptographyManager: CryptographyManager
) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    private val list = mutableListOf<Account>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(list: List<Account>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = list[position]
        holder.binding.tvAccount.text = entity.account
        holder.binding.root.setOnClickListener {
            decryption(entity,holder.binding.tvPassword)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun decryption(account: Account, tvPassword: TextView) {
        val canAuthenticate = BiometricManager
            .from(activity)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            val keyName = activity.getString(R.string.secret_key_name)
            val encryptedBytes: ByteArray = account.password
            val decryptionCipher =
                cryptographyManager.getInitializedCipherForDecryption(keyName, account.iv)
            val info = BiometricPromptUtils.createPromptInfo(activity)
            val biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(activity) { result ->
                    result.cryptoObject?.cipher?.run {
                        val decryptedString = this.doFinal(encryptedBytes).decodeToString()
                        tvPassword.text = decryptedString
                    }
                }
            biometricPrompt.authenticate(info, BiometricPrompt.CryptoObject(decryptionCipher))
        } else {
            Toast.makeText(
                activity,
                activity.getString(R.string.tips_not_add_password_and_fingerprint),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemAccountBinding.bind(itemView)
    }
}