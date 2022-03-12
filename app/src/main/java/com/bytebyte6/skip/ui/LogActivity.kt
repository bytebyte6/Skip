package com.bytebyte6.skip.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bytebyte6.skip.data.AppDataBase
import com.bytebyte6.skip.databinding.ActivityLogBinding

class LogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = LogAdapter()
        binding.recyclerView.apply {
            this.adapter = adapter
        }
        AppDataBase.getAppDataBase(this).logDao().apply {
            this.list().observe(this@LogActivity) {
                adapter.update(it)
            }
        }
    }
}