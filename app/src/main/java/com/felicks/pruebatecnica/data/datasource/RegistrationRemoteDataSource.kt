package com.felicks.pruebatecnica.data.datasource

import com.felicks.pruebatecnica.domain.model.RegistrationData

interface RegistrationRemoteDataSource {
    suspend fun register(data: RegistrationData): Result<Unit>
}
