package com.calyrsoft.ucbp1.features.maintenance.data.repository

import com.calyrsoft.ucbp1.features.maintenance.data.datasource.RemoteConfigDataSource
import com.calyrsoft.ucbp1.features.maintenance.domain.repository.MaintenanceRepository

class MaintenanceRepositoryImpl(
    private val remoteConfigDataSource: RemoteConfigDataSource
) : MaintenanceRepository {

    override suspend fun checkMaintenanceStatus(): Boolean {
        remoteConfigDataSource.fetchConfig()
        return remoteConfigDataSource.isMaintenanceMode()
    }

    override fun getCurrentMaintenanceStatus(): Boolean {
        return remoteConfigDataSource.isMaintenanceMode()
    }
}