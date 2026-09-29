package com.felicks.pruebatecnica

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.felicks.pruebatecnica.data.datasource.FakeRegistrationRemoteDataSource
import com.felicks.pruebatecnica.data.gate.LocationPermissionGateImpl
import com.felicks.pruebatecnica.data.repository.OnboardingRepositoryImpl
import com.felicks.pruebatecnica.domain.usecase.SubmitRegistrationUseCase
import com.felicks.pruebatecnica.presentation.onboarding.OnboardingViewModel
import com.felicks.pruebatecnica.presentation.onboarding.screens.OnboardingContainerScreen
import com.felicks.pruebatecnica.ui.theme.PruebaTecnicaTheme

class MainActivity : ComponentActivity() {

    private val viewModel: OnboardingViewModel by viewModels {
        val remoteDataSource = FakeRegistrationRemoteDataSource()
        val repository = OnboardingRepositoryImpl(remoteDataSource)
        val submitRegistrationUseCase = SubmitRegistrationUseCase(repository)
        val locationPermissionGate = LocationPermissionGateImpl(applicationContext)

        OnboardingViewModel.provideFactory(
            submitRegistrationUseCase = submitRegistrationUseCase,
            locationPermissionGate = locationPermissionGate
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PruebaTecnicaTheme {
                OnboardingContainerScreen(
                    viewModel = viewModel,
                    onFlowCompleted = {
                        Toast.makeText(
                            this,
                            "¡Onboarding completado con éxito!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        }
    }
}