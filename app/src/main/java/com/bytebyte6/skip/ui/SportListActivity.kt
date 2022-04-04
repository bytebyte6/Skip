package com.bytebyte6.skip.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bytebyte6.skip.R
import com.bytebyte6.skip.data.AppDataBase
import com.bytebyte6.skip.data.Data
import com.bytebyte6.skip.databinding.ActivitySportListBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SportListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySportListBinding

    private val executorService: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    private lateinit var adapter: SportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySportListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.add_sport) {
                startActivity(Intent(this, AddSportActivity::class.java))
            }
            true
        }

        adapter = SportAdapter()

        binding.recyclerView.adapter = adapter

        initData()
    }

    private fun initData() {
        AppDataBase.getAppDataBase(this)
            .sportDao()
            .list()
            .observe(this) {
                if (it.isEmpty()) {
                    initDb()
                } else {
                    adapter.update(it)
                }
            }
    }

    private fun initDb() {
        executorService.execute {
            AppDataBase.getAppDataBase(this)
                .sportDao()
                .insert(Data.sports)
        }
    }
}