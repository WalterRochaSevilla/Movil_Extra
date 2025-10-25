package com.calyrsoft.ucbp1.features.maintenance.di

import com.calyrsoft.ucbp1.features.maintenance.data.datasource.RemoteConfigDataSource
import com.calyrsoft.ucbp1.features.maintenance.data.repository.MaintenanceRepositoryImpl
import com.calyrsoft.ucbp1.features.maintenance.domain.repository.MaintenanceRepository
import com.calyrsoft.ucbp1.features.maintenance.domain.usecase.CheckMaintenanceUseCase
import com.calyrsoft.ucbp1.features.maintenance.presentation.viewmodel.MaintenanceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val maintenanceModule = module {
    single { RemoteConfigDataSource() }
    single<MaintenanceRepository> { MaintenanceRepositoryImpl(get()) }
    factory { CheckMaintenanceUseCase(get()) }
    viewModel { MaintenanceViewModel(get()) }
}