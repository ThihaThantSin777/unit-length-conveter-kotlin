package com.example.unitlengthconverter.ui.pages


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitlengthconverter.bloc.LengthConverterViewModel

@Composable
fun HomePage() {
    val viewModel: LengthConverterViewModel = viewModel()
    var inputValue by remember { mutableStateOf("") }
    var fromUnit by remember { mutableStateOf("Metre") }
    var toUnit by remember { mutableStateOf("Foot") }
    var errorMessage by remember { mutableStateOf("") } // State for error message
    val convertedValue by viewModel.convertedValue.collectAsState()

    val units = listOf("Metre", "Millimetre", "Mile", "Foot")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Length Converter", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it.filter { char -> char.isDigit() } },
            label = { Text("Enter value") },
            textStyle = TextStyle(
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownMenuWithLabel("From Unit", units, fromUnit) { selectedUnit ->
            fromUnit = selectedUnit
        }

        Spacer(modifier = Modifier.height(16.dp))

        DropdownMenuWithLabel("To Unit", units, toUnit) { selectedUnit ->
            toUnit = selectedUnit
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (fromUnit == toUnit) {
                    errorMessage = "Cannot process: 'From Unit' and 'To Unit' are the same."
                } else {
                    errorMessage = ""
                    viewModel.convertLength(inputValue, fromUnit, toUnit)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red) // Display error message
        } else {
            Text(text = "Converted Value: $convertedValue")
        }
    }
}

@Composable
fun DropdownMenuWithLabel(label: String, items: List<String>, selectedItem: String, onItemSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(selectedItem)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        },
                        text = { Text(item) }
                    )
                }
            }
        }
    }
}