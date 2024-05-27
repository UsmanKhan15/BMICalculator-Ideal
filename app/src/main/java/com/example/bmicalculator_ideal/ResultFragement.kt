package com.example.bmicalculator_ideal

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bmicalculator_ideal.databinding.FragmentResultFragementBinding
import com.example.bmicalculator_ideal.utils.Utils.Companion.gender
import com.example.bmicalculator_ideal.utils.Utils.Companion.heightFt
import com.example.bmicalculator_ideal.utils.Utils.Companion.heightInches
import com.example.bmicalculator_ideal.utils.Utils.Companion.heightIncm
import com.example.bmicalculator_ideal.utils.Utils.Companion.heightType
import com.example.bmicalculator_ideal.utils.Utils.Companion.weightInKg
import com.example.bmicalculator_ideal.utils.Utils.Companion.weightLb
import com.example.bmicalculator_ideal.utils.Utils.Companion.weightType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class ResultFragement : Fragment() {
    private val binding by lazy { FragmentResultFragementBinding.inflate(layoutInflater) }
    var formattedBMI = ""
    var date1: String = ""
    var bmiResult = ""
    var weight = ""
    var height = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }
    @SuppressLint("SimpleDateFormat", "SetTextI18n", "DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = Calendar.getInstance().time
        val formattedDate = SimpleDateFormat("dd-MMM-yyyy").format(date)
        binding.txtCurrentDate.text = formattedDate
        date1 = formattedDate.toString()

        if (heightType == 1 && weightType == 1) {
            binding.txtHeightResult.text = "$heightIncm Cm"
            binding.txtWeightResult.text = "$weightInKg Kg"

            weight = "$heightIncm Cm"
            height = "$weightInKg Kg"

            val heightInMeters = heightIncm / 100.0
            val bmi = weightInKg / (heightInMeters * heightInMeters)
            formattedBMI = String.format("%.2f", bmi)
            setBMI(formattedBMI)
            binding.txtBMI.text = formattedBMI
        } else if (heightType == 1 && weightType == 2) {
            binding.txtHeightResult.text = "$heightIncm Kg"
            binding.txtWeightResult.text = "$weightLb Lbs"

            weight = "$weightLb Lbs"
            height = "$heightIncm Kg"

            val weightInKg = weightLb / 2.20462
            val heightInMeters = heightIncm / 100.0
            val bmi = weightInKg / (heightInMeters * heightInMeters)
            formattedBMI = String.format("%.2f", bmi)
            setBMI(formattedBMI)
            binding.txtBMI.text = formattedBMI
        } else if (heightType == 2 && weightType == 1) {
            binding.txtHeightResult.text = "$heightFt.$heightInches Feet"
            binding.txtWeightResult.text = weightInKg.toString()

            weight = weightInKg.toString()
            height = "$heightFt.$heightInches Feet"

            val totalHeightInInches = heightFt * 12.0 + heightInches
            val heightInMeters = totalHeightInInches / 39.3701
            val bmi = weightInKg / (heightInMeters * heightInMeters)
            formattedBMI = String.format("%.2f", bmi)
            setBMI(formattedBMI)
            binding.txtBMI.text = formattedBMI
        } else {
            binding.txtHeightResult.text = "$heightFt.$heightInches Feet"
            binding.txtWeightResult.text = weightInKg.toString() + " Kg"

            weight = weightInKg.toString() + " Kg"
            height = "$heightFt.$heightInches Feet"

            val weightInKg = weightLb / 2.20462
            val totalHeightInInches = heightFt * 12.0 + heightInches
            val heightInMeters = totalHeightInInches / 39.3701
            val bmi = weightInKg / (heightInMeters * heightInMeters)
            formattedBMI = String.format("%.2f", bmi)
            setBMI(formattedBMI)
            binding.txtBMI.text = formattedBMI
        }

        binding.txtGenderResult.text = gender

        binding.btnBack.setOnClickListener {
            fragmentManager?.popBackStackImmediate()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setBMI(formattedBMI: String) {
        val resources = requireContext().resources
        if (formattedBMI.toDouble() < 16) {
            binding.txtBMIResult.text = resources.getString(R.string.severe_thinness)
            bmiResult = resources.getString(R.string.severe_thinness1)
            suggestionFirst()
            binding.txtBMIResult.setTextColor(resources.getColor(R.color.blue))
            binding.txtBMI.setTextColor(resources.getColor(R.color.blue))
        } else if (formattedBMI.toDouble() >= 16 && formattedBMI.toDouble() < 17) {
            binding.txtBMIResult.text = resources.getString(R.string.moderate_thinness)
            bmiResult = resources.getString(R.string.moderate_thinness1)
            suggestionFirst()
            binding.txtBMIResult.setTextColor(resources.getColor(R.color.blue))
            binding.txtBMI.setTextColor(resources.getColor(R.color.blue))
        } else if (formattedBMI.toDouble() >= 17 && formattedBMI.toDouble() < 18.5) {
            binding.txtBMIResult.text = resources.getString(R.string.mild_thinness)
            bmiResult = resources.getString(R.string.mild_thinness1)
            suggestionFirst()
            binding.txtBMIResult.setTextColor(resources.getColor(R.color.blue))
            binding.txtBMI.setTextColor(resources.getColor(R.color.blue))
        } else if (formattedBMI.toDouble() >= 18.5 && formattedBMI.toDouble() < 25) {
            binding.txtBMIResult.text = resources.getString(R.string.normal)
            bmiResult = resources.getString(R.string.normal1)
            suggestionSecond()
            binding.txtBMIResult.setTextColor(resources.getColor(R.color.green))
            binding.txtBMI.setTextColor(resources.getColor(R.color.green))
        } else if (formattedBMI.toDouble() >= 25 && formattedBMI.toDouble() < 30) {
            binding.txtBMIResult.text = resources.getString(R.string.overweight)
            bmiResult = resources.getString(R.string.overweight1)
            suggestionThirdFourth()
            binding.txtBMIResult.setTextColor(resources.getColor(R.color.yellow))
            binding.txtBMI.setTextColor(resources.getColor(R.color.yellow))
        } else if (formattedBMI.toDouble() >= 30 && formattedBMI.toDouble() < 35) {
            binding.txtBMIResult.text = resources.getString(R.string.Obese_class_I)
            bmiResult = resources.getString(R.string.Obese_Class_I1)
            suggestionThirdFourth()
            binding.txtBMIResult.setTextColor(resources.getColor(R.color.yellow))
            binding.txtBMI.setTextColor(resources.getColor(R.color.yellow))
        } else if (formattedBMI.toDouble() >= 35 && formattedBMI.toDouble() < 40) {
            binding.txtBMIResult.text = resources.getString(R.string.Obese_Class_II)
            bmiResult = resources.getString(R.string.Obese_Class_II1)
            suggestionThirdFourth()
            binding.txtBMIResult.setTextColor(resources.getColor(R.color.red))
            binding.txtBMI.setTextColor(resources.getColor(R.color.red))
        } else if (formattedBMI.toDouble() >= 40) {
            binding.txtBMIResult.text = resources.getString(R.string.Obese_Class_III)
            bmiResult = resources.getString(R.string.Obese_Class_III1)
            suggestionThirdFourth()
            binding.txtBMIResult.setTextColor(resources.getColor(R.color.red))
            binding.txtBMI.setTextColor(resources.getColor(R.color.red))
        }
    }

    private fun suggestionFirst() {
        binding.imgFirst.setImageResource(R.drawable.icon_active)
        binding.imgSecond.setImageResource(R.drawable.icon_excercise)
        binding.imgThird.setImageResource(R.drawable.icon_sleeping)
        val resources = requireContext().resources
        binding.txtFirst.text = resources.getString(R.string.don_drink_water)
        binding.txtSecond.text = resources.getString(R.string.use_bigger_plates)
        binding.txtThird.text = resources.getString(R.string.quality_sleep)
    }

    private fun suggestionSecond() {
        binding.imgFirst.setImageResource(R.drawable.icon_active)
        binding.imgSecond.setImageResource(R.drawable.icon_excercise)
        binding.imgThird.setImageResource(R.drawable.icon_sleeping)
        val resources = requireContext().resources
        binding.txtFirst.text = resources.getString(R.string.stay_active)
        binding.txtSecond.text = resources.getString(R.string.do_exercises)
        binding.txtThird.text = resources.getString(R.string.focus_on_relaxation_and_sleep)
    }

    private fun suggestionThirdFourth() {
        binding.imgFirst.setImageResource(R.drawable.icon_active)
        binding.imgSecond.setImageResource(R.drawable.icon_excercise)
        binding.imgThird.setImageResource(R.drawable.icon_sleeping)
        val resources = requireContext().resources
        binding.txtFirst.text = resources.getString(R.string.drink_water)
        binding.txtSecond.text = resources.getString(R.string.do_seated)
        binding.txtThird.text = resources.getString(R.string.drink_coffee_or_tea)
    }
}