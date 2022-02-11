package com.bytebyte6.skip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bytebyte6.skip.databinding.ActivityAddSportBinding

class AddSportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId == R.id.rbGroup) {
                    etMinGroupLayout.isEnabled = true
                    etMaxGroupLayout.isEnabled = true
                    etMinCountLayout.isEnabled = true
                    etMaxCountLayout.isEnabled = true
                    etGroupRestDurationLayout.isEnabled = true
                    etMinDurationLayout.isEnabled = false
                    etMaxDurationLayout.isEnabled = false
                } else {
                    etMinGroupLayout.isEnabled = false
                    etMaxGroupLayout.isEnabled = false
                    etMinCountLayout.isEnabled = false
                    etMaxCountLayout.isEnabled = false
                    etGroupRestDurationLayout.isEnabled = false
                    etMinDurationLayout.isEnabled = true
                    etMaxDurationLayout.isEnabled = true
                }
            }
        }
    }
}