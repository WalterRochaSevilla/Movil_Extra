package com.calyrsoft.ucbp1.features.maintenance.domain.repository

interface MaintenanceRepository {
    suspend fun checkMaintenanceStatus(): Boolean
    fun getCurrentMaintenanceStatus(): Boolean
}