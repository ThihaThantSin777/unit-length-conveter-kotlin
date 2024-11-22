package com.example.unitlengthconverter.utils

fun isValidInput(input: String): Boolean {
    return input.toDoubleOrNull() != null && input.isNotBlank()
}