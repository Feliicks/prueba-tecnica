package com.felicks.pruebatecnica.data.datasource

import com.felicks.pruebatecnica.domain.model.RegistrationData
import kotlinx.coroutines.delay

class FakeRegistrationRemoteDataSource(
    private val simulatedDelayMs: Long = 1000L,
    private val shouldSucceed: Boolean = true
) : RegistrationRemoteDataSource {
    override suspend fun register(data: RegistrationData): Result<Unit> {
        if (simulatedDelayMs > 0) {
            delay(simulatedDelayMs)
        }
        return if (shouldSucceed) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Error al registrar los datos. Por favor, intenta de nuevo."))
        }
    }
}
