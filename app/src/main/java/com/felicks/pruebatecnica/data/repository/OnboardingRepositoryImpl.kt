package com.felicks.pruebatecnica.data.repository

import com.felicks.pruebatecnica.data.datasource.RegistrationRemoteDataSource
import com.felicks.pruebatecnica.domain.model.RegistrationData
import com.felicks.pruebatecnica.domain.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val remoteDataSource: RegistrationRemoteDataSource
) : OnboardingRepository {
    override suspend fun submitRegistration(data: RegistrationData): Result<Unit> {
        return remoteDataSource.register(data)
    }
}
