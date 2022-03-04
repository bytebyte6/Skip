package com.bytebyte6.skip.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bytebyte6.skip.*
import com.bytebyte6.skip.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.registerReceiver(pongReceiver, IntentFilter(SkipService.PONG))
        binding.button.setOnClickListener {
            goToSettingsScreen()
        }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.account -> startActivity(Intent(requireContext(), AccountActivity::class.java))
                R.id.log -> startActivity(Intent(requireContext(), LogActivity::class.java))
            }
            true
        }
        activity?.sendBroadcast(Intent(SkipService.PING))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        activity?.unregisterReceiver(pongReceiver)
    }

    private val pongReceiver = PongReceiver()

    private fun goToSettingsScreen() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    inner class PongReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            binding.button.isEnabled = false
            binding.textView.setText(R.string.skip_service_enable)
        }
    }
}