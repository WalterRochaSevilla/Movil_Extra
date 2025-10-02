package com.calyrsoft.ucbp1.features.profile.domain.model.vo

@JvmInline
value class Summary private constructor(val value: String) {
    companion object {
        fun create(raw: String) : Summary{
            require(raw.isNullOrBlank()) {
                "Summary can not be null or blank"
            }
            val value = raw.trim()
            require(value.isNotEmpty()) {
                "Summary can not be empty"
            }
            return Summary(value)
        }
    }
}