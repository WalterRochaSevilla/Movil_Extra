package com.calyrsoft.ucbp1.features.maintenance.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.maintenance.domain.usecase.CheckMaintenanceUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MaintenanceViewModel(
    private val checkMaintenanceUseCase: CheckMaintenanceUseCase
) : ViewModel() {

    private val _maintenanceState = MutableStateFlow(false)
    val maintenanceState: StateFlow<Boolean> = _maintenanceState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    // NUEVO: Flow para forzar actualizaciones periódicas
    private val _refreshTrigger = MutableStateFlow(0)

    init {
        // Iniciar verificación periódica automática
        startPeriodicChecks()
    }

    private fun startPeriodicChecks() {
        viewModelScope.launch {
            // Verificar cada 30 segundos mientras la app esté activa
            while (true) {
                delay(30000) // 30 segundos
                Log.d("Maintenance", "🔄 Verificación periódica automática")
                checkMaintenanceInternal()
            }
        }
    }

    fun checkMaintenance() {
        viewModelScope.launch {
            checkMaintenanceInternal()
        }
    }

    private suspend fun checkMaintenanceInternal() {
        _loadingState.value = true
        try {
            val isMaintenance = checkMaintenanceUseCase.execute()
            Log.d("Maintenance", "🔄 Nuevo estado: $isMaintenance (anterior: ${_maintenanceState.value})")

            // Solo actualizar si el estado cambió
            if (_maintenanceState.value != isMaintenance) {
                _maintenanceState.value = isMaintenance
                Log.d("Maintenance", "✅ Estado actualizado: $isMaintenance")
            }
        } catch (e: Exception) {
            Log.e("Maintenance", "❌ Error en checkMaintenance", e)
            // En caso de error, mantener el estado actual
        } finally {
            _loadingState.value = false
        }
    }
}