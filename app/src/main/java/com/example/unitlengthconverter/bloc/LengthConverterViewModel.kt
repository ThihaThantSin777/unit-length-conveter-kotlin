package com.example.unitlengthconverter.bloc


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LengthConverterViewModel : ViewModel() {
    private val _convertedValue = MutableStateFlow("")
    val convertedValue: StateFlow<String> = _convertedValue

    fun convertLength(input: String, fromUnit: String, toUnit: String) {
        viewModelScope.launch {
            if (input.isNotBlank()) {
                val inputValue = input.toDoubleOrNull() ?: 0.0
                val result = when (fromUnit) {
                    "Metre" -> convertFromMetre(inputValue, toUnit)
                    "Millimetre" -> convertFromMillimetre(inputValue, toUnit)
                    "Mile" -> convertFromMile(inputValue, toUnit)
                    "Foot" -> convertFromFoot(inputValue, toUnit)
                    else -> 0.0
                }
                _convertedValue.value = result.toString()
            } else {
                _convertedValue.value = "Invalid Input"
            }
        }
    }

    private fun convertFromMetre(value: Double, toUnit: String): Double {
        return when (toUnit) {
            "Millimetre" -> value * 1000
            "Mile" -> value / 1609.34
            "Foot" -> value * 3.28084
            else -> value
        }
    }

    private fun convertFromMillimetre(value: Double, toUnit: String): Double {
        return when (toUnit) {
            "Metre" -> value / 1000
            "Mile" -> value / 1_609_344
            "Foot" -> value / 304.8
            else -> value
        }
    }

    private fun convertFromMile(value: Double, toUnit: String): Double {
        return when (toUnit) {
            "Metre" -> value * 1609.34
            "Millimetre" -> value * 1_609_344
            "Foot" -> value * 5280
            else -> value
        }
    }

    private fun convertFromFoot(value: Double, toUnit: String): Double {
        return when (toUnit) {
            "Metre" -> value / 3.28084
            "Millimetre" -> value * 304.8
            "Mile" -> value / 5280
            else -> value
        }
    }
}
