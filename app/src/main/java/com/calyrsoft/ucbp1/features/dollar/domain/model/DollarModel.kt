package com.calyrsoft.ucbp1.features.dollar.domain.model

data class DollarModel(
    var type: TYPE? = null,
    var salesPrice: String? = null,
    var purchasePrice: String? = null
)

enum class TYPE {
    OFFICIAL, PARALLEL
}