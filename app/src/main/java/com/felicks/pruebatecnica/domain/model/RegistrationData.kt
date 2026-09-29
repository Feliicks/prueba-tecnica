package com.felicks.pruebatecnica.domain.model

data class RegistrationData(
    val phone: String,
    val carnet: String,
    val complement: String? = null
)
