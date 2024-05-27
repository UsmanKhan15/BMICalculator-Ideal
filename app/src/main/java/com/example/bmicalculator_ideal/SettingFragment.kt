package com.example.bmicalculator_ideal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bmicalculator_ideal.databinding.FragmentSettingBinding
import com.example.bmicalculator_ideal.utils.Utils.Companion.contactUs
import com.example.bmicalculator_ideal.utils.Utils.Companion.openLink
import com.example.bmicalculator_ideal.utils.Utils.Companion.openPlayStoreForRating
import com.example.bmicalculator_ideal.utils.Utils.Companion.shareApp

class SettingFragment : Fragment() {
    private val binding by lazy {
        FragmentSettingBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.parentRateUs.setOnClickListener {
            openPlayStoreForRating(requireActivity())
        }
        binding.parentPrivacyPolicy.setOnClickListener {
            openLink(
                requireActivity(),
                "https://sites.google.com/view/privacypolicy-bmi/home"
            )
        }
        binding.parentShareApp.setOnClickListener {
            shareApp(requireActivity())
        }
        binding.parentTerms.setOnClickListener {
            openLink(
                requireActivity(),
                "https://sites.google.com/view/termsbmicalculator/home"
            )
        }
        binding.parentFeedback.setOnClickListener {
            contactUs(requireActivity())
        }
        binding.btnBack.setOnClickListener {
            fragmentManager?.popBackStackImmediate()
        }
        return binding.root
    }
}