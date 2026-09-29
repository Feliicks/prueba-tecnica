package com.felicks.pruebatecnica.domain.usecase

import com.felicks.pruebatecnica.domain.model.ValidationResult

class ValidatePhoneUseCase {
    operator fun invoke(phone: String): ValidationResult {
        if (phone.isBlank()) {
            return ValidationResult.Invalid("El número de celular es requerido")
        }
        if (!phone.all { it.isDigit() }) {
            return ValidationResult.Invalid("El número de celular solo debe contener dígitos")
        }
        if (phone.length != 8) {
            return ValidationResult.Invalid("El número de celular debe tener 8 dígitos")
        }
        return ValidationResult.Valid
    }
}
