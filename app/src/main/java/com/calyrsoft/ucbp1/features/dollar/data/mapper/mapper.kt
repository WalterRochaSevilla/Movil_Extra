package com.calyrsoft.ucbp1.features.dollar.data.mapper

import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.model.TYPE

fun DollarEntity.toModel() : DollarModel {
    return DollarModel(
        salesPrice = salesPrice,
        purchasePrice = purchasePrice,
        type = TYPE.valueOf(type ?: TYPE.OFFICIAL.name)
    )
}

fun DollarModel.toEntity() : DollarEntity {
    return DollarEntity(
        purchasePrice = purchasePrice,
        salesPrice = salesPrice,
        type = type?.name
    )
}

