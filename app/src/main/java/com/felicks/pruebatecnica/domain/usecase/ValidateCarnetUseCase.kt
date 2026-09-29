package com.felicks.pruebatecnica.domain.usecase

import com.felicks.pruebatecnica.domain.model.ValidationResult

class ValidateCarnetUseCase {
    operator fun invoke(carnet: String): ValidationResult {
        if (carnet.isBlank()) {
            return ValidationResult.Invalid("El carnet de identidad es requerido")
        }
        if (!carnet.all { it.isDigit() }) {
            return ValidationResult.Invalid("El carnet solo debe contener dígitos")
        }
        if (carnet.length < 5 || carnet.length > 10) {
            return ValidationResult.Invalid("El carnet debe tener entre 5 y 10 dígitos")
        }
        return ValidationResult.Valid
    }
}
