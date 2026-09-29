package com.felicks.pruebatecnica.domain.repository

import com.felicks.pruebatecnica.domain.model.RegistrationData

interface OnboardingRepository {
    suspend fun submitRegistration(data: RegistrationData): Result<Unit>
}
