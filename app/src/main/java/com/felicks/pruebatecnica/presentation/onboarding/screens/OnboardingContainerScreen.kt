package com.felicks.pruebatecnica.presentation.onboarding.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.felicks.pruebatecnica.presentation.onboarding.OnboardingIntent
import com.felicks.pruebatecnica.presentation.onboarding.OnboardingStep
import com.felicks.pruebatecnica.presentation.onboarding.OnboardingViewModel
import com.felicks.pruebatecnica.presentation.onboarding.components.LocationRationaleModal

@Composable
fun OnboardingContainerScreen(
    viewModel: OnboardingViewModel,
    modifier: Modifier = Modifier,
    onFlowCompleted: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val fineGranted = permissionsMap[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = permissionsMap[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        viewModel.onIntent(
            OnboardingIntent.OnLocationPermissionResult(isGranted = fineGranted || coarseGranted)
        )
    }

    // Display transient feedback messages
    LaunchedEffect(uiState.userFeedbackMessage) {
        val message = uiState.userFeedbackMessage
        if (message != null) {
            snackbarHostState.showSnackbar(message)
            viewModel.onIntent(OnboardingIntent.OnFeedbackDismissed)
        }
    }

    // React to flow completion
    LaunchedEffect(uiState.isFlowCompleted) {
        if (uiState.isFlowCompleted) {
            onFlowCompleted()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        AnimatedContent(
            targetState = uiState.currentStep,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            modifier = Modifier.padding(innerPadding),
            label = "onboarding_step_transition"
        ) { step ->
            when (step) {
                OnboardingStep.INFORMACION -> {
                    InformacionScreen(
                        uiState = uiState,
                        onPhoneChanged = { viewModel.onIntent(OnboardingIntent.OnPhoneChanged(it)) },
                        onCarnetChanged = { viewModel.onIntent(OnboardingIntent.OnCarnetChanged(it)) },
                        onComplementChanged = { viewModel.onIntent(OnboardingIntent.OnComplementChanged(it)) },
                        onSubmitClicked = { viewModel.onIntent(OnboardingIntent.OnSubmitClicked) }
                    )
                }

                OnboardingStep.AUTENTICACION -> {
                    AutenticacionScreen(
                        uiState = uiState,
                        onPageChanged = { viewModel.onIntent(OnboardingIntent.OnTipPageChanged(it)) },
                        onNextClicked = { viewModel.onIntent(OnboardingIntent.OnNextTipClicked) },
                        onStartVerificationClicked = { viewModel.onIntent(OnboardingIntent.OnStartVerificationClicked) },
                        onBackClicked = {
                            // Back to step 1
                            viewModel.onIntent(OnboardingIntent.OnTipPageChanged(0))
                        }
                    )
                }
            }
        }

        // Location Rationale Dialog
        if (uiState.showLocationRationale) {
            LocationRationaleModal(
                onConfirm = {
                    viewModel.onIntent(OnboardingIntent.OnRationaleConfirmed)
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                },
                onDismiss = {
                    viewModel.onIntent(OnboardingIntent.OnRationaleDismissed)
                }
            )
        }
    }
}
