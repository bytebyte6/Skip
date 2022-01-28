package com.bytebyte6.skip

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bytebyte6.skip.data.Account
import com.bytebyte6.skip.data.AccountDao
import com.bytebyte6.skip.data.AppDataBase
import com.bytebyte6.skip.databinding.ActivityAccountBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var accountDao: AccountDao
    private val service = Executors.newSingleThreadExecutor()

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
                    service.execute{
                        accountDao.insert(Account(account = account, password = password))
                    }
                }
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