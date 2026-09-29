package com.felicks.pruebatecnica.domain.model

sealed interface ValidationResult {
    object Valid : ValidationResult
    data class Invalid(val errorMessage: String) : ValidationResult
}

val ValidationResult.isValid: Boolean
    get() = this is ValidationResult.Valid
