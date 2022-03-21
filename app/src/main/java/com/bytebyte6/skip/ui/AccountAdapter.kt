package com.bytebyte6.skip.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
            decryption(entity)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun decryption(account: Account) {
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
                        showDialog(decryptedString)
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

    private fun showDialog(decryptedString: String) {
        AlertDialog.Builder(activity)
            .setMessage(decryptedString)
            .setPositiveButton(R.string.copy) { _, _ ->
                val clipboard =
                    activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(
                    ClipData.newPlainText("text", decryptedString)
                )
            }
            .create()
            .show()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemAccountBinding.bind(itemView)
    }
}