package com.felicks.pruebatecnica.domain.usecase

import com.felicks.pruebatecnica.domain.model.RegistrationData
import com.felicks.pruebatecnica.domain.repository.OnboardingRepository

class SubmitRegistrationUseCase(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke(data: RegistrationData): Result<Unit> {
        return repository.submitRegistration(data)
    }
}
