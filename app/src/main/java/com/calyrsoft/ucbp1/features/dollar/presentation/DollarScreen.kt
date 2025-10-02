package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun DollarScreen(viewModelDollar: DollarViewModel = koinViewModel()) {
    val state = viewModelDollar.uiState.collectAsState()
    val stateParallel = viewModelDollar.uiStateParallel.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {

        // 🔹 Dólar Oficial
        Text("💰 Dólar Oficial", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        when (val stateValue = state.value) {
            is DollarViewModel.DollarUIState.Error -> Text("Error: ${stateValue.message}")
            DollarViewModel.DollarUIState.Loading -> CircularProgressIndicator()
            is DollarViewModel.DollarUIState.Success -> {
                Text("Compra: ${stateValue.data.purchasePrice ?: "N/A"}")
                Text("Venta: ${stateValue.data.salesPrice ?: "N/A"}")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Dólar Paralelo
        Text("💸 Dólar Paralelo", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        when (val stateValueParallel = stateParallel.value) {
            is DollarViewModel.DollarUIState.Error -> Text("Error: ${stateValueParallel.message}")
            DollarViewModel.DollarUIState.Loading -> CircularProgressIndicator()
            is DollarViewModel.DollarUIState.Success -> {
                Text("Compra: ${stateValueParallel.data.purchasePrice ?: "N/A"}")
                Text("Venta: ${stateValueParallel.data.salesPrice ?: "N/A"}")
            }
        }
    }
}
