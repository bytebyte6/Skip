package com.bytebyte6.skip

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.bytebyte6.skip.data.TrainingWay
import com.bytebyte6.skip.databinding.ActivityAddSportBinding

class AddSportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSportBinding

    private val viewModel: AddSportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            toolbar.setOnMenuItemClickListener {
                viewModel.addSport()
                true
            }
            setupCheckChangeListener()
            setupTextWatcher()
        }
        setupObserver()
    }

    private fun ActivityAddSportBinding.setupCheckChangeListener() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbGroup) {
                viewModel.sport.trainingWay = TrainingWay.BY_GROUP
                etMinGroupLayout.isEnabled = true
                etMaxGroupLayout.isEnabled = true
                etMinCountLayout.isEnabled = true
                etMaxCountLayout.isEnabled = true
                etGroupRestDurationLayout.isEnabled = true
                etMinDurationLayout.isEnabled = false
                etMaxDurationLayout.isEnabled = false
            } else {
                viewModel.sport.trainingWay = TrainingWay.BY_TIME
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

    private fun ActivityAddSportBinding.setupTextWatcher() {
        etMinCount.doOnTextChanged { text, _, _, _ ->
            viewModel.sport.minCount = text.toInt()
            etMinCountLayout.isErrorEnabled = false
        }
        etMaxCount.doOnTextChanged { text, _, _, _ ->
            viewModel.sport.maxCount = text.toInt()
            etMaxCountLayout.isErrorEnabled = false
        }
        etMinGroupCount.doOnTextChanged { text, _, _, _ ->
            viewModel.sport.minGroup = text.toInt()
            etMinGroupLayout.isErrorEnabled = false
        }
        etMaxGroupCount.doOnTextChanged { text, _, _, _ ->
            viewModel.sport.maxGroup = text.toInt()
            etMaxGroupLayout.isErrorEnabled = false
        }
        etSportName.doOnTextChanged { text, _, _, _ ->
            viewModel.sport.name = text.toStringOrEmpty()
            etSportNameLayout.isErrorEnabled = false
        }
        etGroupRestDuration.doOnTextChanged { text, _, _, _ ->
            viewModel.sport.groupRestDuration = text.toInt()
            etGroupRestDurationLayout.isErrorEnabled = false
        }
        etMinDuration.doOnTextChanged { text, _, _, _ ->
            viewModel.sport.minDuration = text.toInt()
            etMinDurationLayout.isErrorEnabled = false
        }
        etMaxDuration.doOnTextChanged { text, _, _, _ ->
            viewModel.sport.maxDuration = text.toInt()
            etMaxDurationLayout.isErrorEnabled = false
        }
        etRestDuration.doOnTextChanged { text, _, _, _ ->
            viewModel.sport.restDuration = text.toInt()
            etRestDurationLayout.isErrorEnabled = false
        }
    }

    private fun setupObserver() {
        viewModel.nameError.observe(this, EventObserver {
            binding.etSportNameLayout.error = getString(R.string.empty_tips)
        })
        viewModel.groupRestDurationError.observe(this, EventObserver {
            binding.etGroupRestDurationLayout.error = getString(R.string.empty_tips)
        })
        viewModel.maxCountError.observe(this, EventObserver {
            binding.etMaxCountLayout.error = getString(R.string.empty_tips)
        })
        viewModel.minCountError.observe(this, EventObserver {
            binding.etMinCountLayout.error = getString(R.string.empty_tips)
        })
        viewModel.maxDurationError.observe(this, EventObserver {
            binding.etMaxDurationLayout.error = getString(R.string.empty_tips)
        })
        viewModel.minDurationError.observe(this, EventObserver {
            binding.etMinDurationLayout.error = getString(R.string.empty_tips)
        })
        viewModel.maxGroupError.observe(this, EventObserver {
            binding.etMaxGroupLayout.error = getString(R.string.empty_tips)
        })
        viewModel.minGroupError.observe(this, EventObserver {
            binding.etMinGroupLayout.error = getString(R.string.empty_tips)
        })
        viewModel.restDurationError.observe(this, EventObserver {
            binding.etRestDurationLayout.error = getString(R.string.empty_tips)
        })
    }

}