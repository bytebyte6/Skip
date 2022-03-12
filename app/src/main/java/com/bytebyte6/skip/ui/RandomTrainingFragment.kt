package com.bytebyte6.skip.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bytebyte6.skip.*
import com.bytebyte6.skip.databinding.FragmentRandomTrainingBinding

class RandomTrainingFragment : Fragment() {

    private var _binding: FragmentRandomTrainingBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<TodayTrainingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRandomTrainingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.add_sport) {
                startActivity(Intent(requireContext(), AddSportActivity::class.java))
            } else {
                startActivity(Intent(requireContext(), SportListActivity::class.java))
            }
            true
        }

        val realSportAdapter = RealSportAdapter()
        binding.recyclerView.adapter = realSportAdapter

        viewModel.sportPlanUI.observe(viewLifecycleOwner) {
            if (it != null) {
                realSportAdapter.update(it.list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}