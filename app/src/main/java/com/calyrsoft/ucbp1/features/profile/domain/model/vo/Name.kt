package com.calyrsoft.ucbp1.features.profile.domain.model.vo

@JvmInline
value class Name private constructor(val value: String) {

    companion object {
        fun create(raw: String): Name {
            require(raw.isNullOrBlank()) {
                "Name can not be null or blank"
            }
            val value = raw.trim()
            require(value.isNotEmpty()) {
                "Name can not be empty"
            }
            return Name(value)
        }
    }
}