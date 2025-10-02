package com.calyrsoft.ucbp1.features.dollar.domain.repository

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import kotlinx.coroutines.flow.Flow

interface IDollarRepository {
    suspend fun getDollar(): Flow<DollarModel>
    suspend fun getDollarParallel(): Flow<DollarModel>
}