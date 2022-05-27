package com.bytebyte6.skip.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.bytebyte6.skip.*
import com.bytebyte6.skip.data.AppDataBase
import com.bytebyte6.skip.data.Sport
import com.bytebyte6.skip.data.TrainingWay
import com.bytebyte6.skip.data.byGroup
import com.bytebyte6.skip.databinding.ActivityAddSportBinding

class AddSportActivity : AppCompatActivity() {

    companion object {
        const val KEY_ID = "ID"
    }

    private lateinit var binding: ActivityAddSportBinding

    private val viewModel: AddSportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(KEY_ID, -1)

        binding.run {
            toolbar.setOnMenuItemClickListener {
                viewModel.save(id)
                true
            }
            setupCheckChangeListener()
            setupTextWatcher()
        }
        setupObserver()
        // 编辑模式
        recoverIfIdNotEmpty(id)
    }

    private fun recoverIfIdNotEmpty(id: Int) {
        if (id != -1) {
            AppDataBase.getAppDataBase(this)
                .sportDao()
                .liveData(id)
                .observe(this) {
                    viewModel.sport = it
                    binding.recover(it)
                }
        }
    }

    private fun ActivityAddSportBinding.setupCheckChangeListener() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbGroup) {
                setupByGroup()
            } else {
                setupByTime()
            }
        }
    }

    private fun ActivityAddSportBinding.setupByTime() {
        viewModel.sport.trainingWay = TrainingWay.BY_TIME
        etMinGroupLayout.isEnabled = false
        etMaxGroupLayout.isEnabled = false
        etMinCountLayout.isEnabled = false
        etMaxCountLayout.isEnabled = false
        etGroupRestDurationLayout.isEnabled = false
        etMinDurationLayout.isEnabled = true
        etMaxDurationLayout.isEnabled = true
    }

    private fun ActivityAddSportBinding.setupByGroup() {
        viewModel.sport.trainingWay = TrainingWay.BY_GROUP
        etMinGroupLayout.isEnabled = true
        etMaxGroupLayout.isEnabled = true
        etMinCountLayout.isEnabled = true
        etMaxCountLayout.isEnabled = true
        etGroupRestDurationLayout.isEnabled = true
        etMinDurationLayout.isEnabled = false
        etMaxDurationLayout.isEnabled = false
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
        viewModel.onSaveSuccess.observe(this,EventObserver{
            finish()
        })
    }

    private fun ActivityAddSportBinding.recover(sport: Sport) {
        binding.etSportName.postDelayed({
            if (sport.trainingWay.byGroup()) {
                radioGroup.check(R.id.rbGroup)
                setupByGroup()
            } else {
                radioGroup.check(R.id.rbTime)
                setupByTime()
            }
            etSportName.setText(sport.name)
            etMinGroupCount.setText(sport.minGroup.toString())
            etMaxGroupCount.setText(sport.maxGroup.toString())
            etMinCount.setText(sport.minCount.toString())
            etMaxCount.setText(sport.maxCount.toString())
            etGroupRestDuration.setText(sport.groupRestDuration.toString())
            etMinDuration.setText(sport.minDuration.toString())
            etMaxDuration.setText(sport.maxDuration.toString())
            etRestDuration.setText(sport.restDuration.toString())
        },300)
    }
}