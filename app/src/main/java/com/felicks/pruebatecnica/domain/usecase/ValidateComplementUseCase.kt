package com.felicks.pruebatecnica.domain.usecase

import com.felicks.pruebatecnica.domain.model.ValidationResult

class ValidateComplementUseCase {
    private val regex = Regex("^[a-zA-Z0-9]*$")

    operator fun invoke(complement: String?): ValidationResult {
        if (complement.isNullOrBlank()) {
            return ValidationResult.Valid
        }
        if (complement.length > 2) {
            return ValidationResult.Invalid("El complemento no debe exceder 2 caracteres")
        }
        if (!regex.matches(complement)) {
            return ValidationResult.Invalid("El complemento solo debe contener caracteres alfanuméricos")
        }
        return ValidationResult.Valid
    }
}
