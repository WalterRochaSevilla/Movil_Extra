package com.calyrsoft.ucbp1.features.maintenance.domain.usecase

import com.calyrsoft.ucbp1.features.maintenance.domain.repository.MaintenanceRepository

class CheckMaintenanceUseCase(
    private val repository: MaintenanceRepository
) {
    suspend fun execute(): Boolean = repository.checkMaintenanceStatus()
    fun getCurrentStatus(): Boolean = repository.getCurrentMaintenanceStatus()
}