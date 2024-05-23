package com.example.bmicalculator_ideal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bmicalculator_ideal.utils.SharedPreference
import com.example.bmicalculator_ideal.utils.Utils.Companion.gender
import com.example.bmicalculator_ideal.utils.Utils.Companion.heightFt
import com.example.bmicalculator_ideal.utils.Utils.Companion.heightInches
import com.example.bmicalculator_ideal.utils.Utils.Companion.heightIncm
import com.example.bmicalculator_ideal.utils.Utils.Companion.heightType
import com.example.bmicalculator_ideal.utils.Utils.Companion.weightInKg
import com.example.bmicalculator_ideal.utils.Utils.Companion.weightLb
import com.example.bmicalculator_ideal.utils.Utils.Companion.weightType
import com.example.bmicalculator_ideal.databinding.FragmentHomeBinding
import java.util.Calendar
import java.util.Date

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtMale.setBackgroundResource(R.drawable.bg_green_empty_less_radius)
        binding.txtMale.setTextColor(resources.getColor(R.color.appcolor))
        binding.txtFemale.setBackgroundResource(R.drawable.bg_language_not_selected)

        if (SharedPreference.getString("value", "1").equals("1")) {

        } else {
            val dateOfBirth = SharedPreference.getString("dateofbirth", "")
            Log.d("MyCheck", "onViewCreated: $dateOfBirth")
            if (dateOfBirth.isEmpty()) {
                return
            }
            val dateOfBirthComponents = dateOfBirth.split("/")
            val dayOfBirth = dateOfBirthComponents[0].toInt()
            val monthOfBirth = dateOfBirthComponents[1].toInt()
            val yearOfBirth = dateOfBirthComponents[2].toInt()

            val today = Date()
            val currentCalendar = Calendar.getInstance()
            currentCalendar.time = today

            val userCalendar = Calendar.getInstance()
            userCalendar.set(yearOfBirth, monthOfBirth - 1, dayOfBirth)

            val ageInYears = currentCalendar.get(Calendar.YEAR) - userCalendar.get(Calendar.YEAR)

// Calculate adjusted age considering birthday in current year
            val adjustedAge = ageInYears - (if (currentCalendar.get(Calendar.DAY_OF_YEAR) < userCalendar.get(Calendar.DAY_OF_YEAR) ||
                (currentCalendar.get(Calendar.DAY_OF_YEAR) == userCalendar.get(Calendar.DAY_OF_YEAR) &&
                        currentCalendar.get(Calendar.MONTH) < userCalendar.get(Calendar.MONTH))) 1 else 0)

        }

        binding.btnSetting.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }
        binding.btnCalculate.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_resultFragement)
        }


        binding.txtMale.setOnClickListener {
            gender = "Male"
            binding.txtMale.setBackgroundResource(R.drawable.bg_green_empty_less_radius)
            binding.txtMale.setTextColor(resources.getColor(R.color.appcolor))
            binding.txtFemale.setTextColor(resources.getColor(R.color.appgray))
            binding.txtFemale.setBackgroundResource(R.drawable.bg_language_not_selected)
        }

        binding.txtFemale.setOnClickListener {
            gender = "Female"
            binding.txtFemale.setBackgroundResource(R.drawable.bg_green_empty_less_radius)
            binding.txtFemale.setTextColor(resources.getColor(R.color.appcolor))
            binding.txtMale.setTextColor(resources.getColor(R.color.appgray))
            binding.txtMale.setBackgroundResource(R.drawable.bg_language_not_selected)
        }

        val options = listOf("feet", "cm")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, options)
        binding.unitsSpinner.adapter = adapter
        binding.unitsSpinner.setSelection(0)

        binding.unitsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = parent?.getItemAtPosition(position) as String
                Log.d("Selectedoption:", selectedOption)
                if(selectedOption == "cm"){
                    heightType = 1
                    binding.edtHeightCm.visibility = View.VISIBLE
                    binding.edtFeetHeight.visibility = View.INVISIBLE
                    binding.edtHeightInches.visibility = View.INVISIBLE
                }else{
                    heightType = 2
                    binding.edtHeightCm.visibility = View.GONE
                    binding.edtFeetHeight.visibility = View.VISIBLE
                    binding.edtHeightInches.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val optionsWeight = listOf("kg", "lbs")
        val adapterWeight = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, optionsWeight)
        binding.spinner.adapter = adapterWeight
        binding.spinner.setSelection(0)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = parent?.getItemAtPosition(position) as String
                Log.d("Selectedoption:", selectedOption)
                if(selectedOption == "kg")
                    weightType = 1
                else
                    weightType = 2
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        binding.btnCalculate.setOnClickListener {
            if(heightType == 1)
            {
                if(binding.edtHeightCm.text.isEmpty() ||  binding.edtWeight.text.isEmpty() ||
                    binding.edtWeight.text.isBlank() || binding.edtHeightCm.text.isBlank() ||
                    binding.edtHeightCm.text.toString().toInt() < 100 || binding.edtHeightCm.text.toString().toInt() > 250 ||
                    binding.edtWeight.text.toString().toInt() < 5 || binding.edtWeight.text.toString().toInt() > 350)
                {
                    Toast.makeText(requireContext(), "Please fill all the fields with valid values", Toast.LENGTH_SHORT).show()
                }
                else{
                    heightIncm = binding.edtHeightCm.text.toString().toInt()
                    if(weightType == 1)
                        weightInKg = binding.edtWeight.text.toString().toInt()
                    else
                        weightLb = binding.edtWeight.text.toString().toInt()
                    findNavController().navigate(R.id.action_homeFragment_to_resultFragement)
                }
            }
            else{
                if(binding.edtFeetHeight.text.isEmpty() || binding.edtHeightInches.text.isEmpty() ||
                    binding.edtWeight.text.isEmpty() || binding.edtWeight.text.isBlank() ||
                    binding.edtFeetHeight.text.isBlank() || binding.edtHeightInches.text.isBlank() ||
                    binding.edtFeetHeight.text.toString().toInt() < 1 || binding.edtFeetHeight.text.toString().toInt() > 10 ||
                    binding.edtHeightInches.text.toString().toInt() < 0 || binding.edtHeightInches.text.toString().toInt() > 12){
                    Toast.makeText(requireContext(), "Please fill all the fields with valid values", Toast.LENGTH_SHORT).show()
                }
                else{
                    heightFt = binding.edtFeetHeight.text.toString().toInt()
                    heightInches = binding.edtHeightInches.text.toString().toInt()

                    if(weightType == 1)
                        weightInKg = binding.edtWeight.text.toString().toInt()
                    else
                        weightLb = binding.edtWeight.text.toString().toInt()
                    findNavController().navigate(R.id.action_homeFragment_to_resultFragement)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.edtHeightCm.setText("")
        binding.edtWeight.setText("")
        binding.edtFeetHeight.setText("")
        binding.edtHeightInches.setText("")
    }
}