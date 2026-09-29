package com.felicks.pruebatecnica.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.felicks.pruebatecnica.domain.gate.LocationPermissionGate
import com.felicks.pruebatecnica.domain.model.RegistrationData
import com.felicks.pruebatecnica.domain.model.ValidationResult
import com.felicks.pruebatecnica.domain.usecase.SubmitRegistrationUseCase
import com.felicks.pruebatecnica.domain.usecase.ValidateCarnetUseCase
import com.felicks.pruebatecnica.domain.usecase.ValidateComplementUseCase
import com.felicks.pruebatecnica.domain.usecase.ValidatePhoneUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val validatePhoneUseCase: ValidatePhoneUseCase = ValidatePhoneUseCase(),
    private val validateCarnetUseCase: ValidateCarnetUseCase = ValidateCarnetUseCase(),
    private val validateComplementUseCase: ValidateComplementUseCase = ValidateComplementUseCase(),
    private val submitRegistrationUseCase: SubmitRegistrationUseCase,
    private val locationPermissionGate: LocationPermissionGate
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun onIntent(intent: OnboardingIntent) {
        when (intent) {
            is OnboardingIntent.OnPhoneChanged -> handlePhoneChanged(intent.value)
            is OnboardingIntent.OnCarnetChanged -> handleCarnetChanged(intent.value)
            is OnboardingIntent.OnComplementChanged -> handleComplementChanged(intent.value)
            is OnboardingIntent.OnSubmitClicked -> handleSubmitClicked()
            is OnboardingIntent.OnRationaleDismissed -> handleRationaleDismissed()
            is OnboardingIntent.OnRationaleConfirmed -> handleRationaleConfirmed()
            is OnboardingIntent.OnLocationPermissionResult -> handleLocationPermissionResult(intent.isGranted)
            is OnboardingIntent.OnTipPageChanged -> handleTipPageChanged(intent.pageIndex)
            is OnboardingIntent.OnNextTipClicked -> handleNextTipClicked()
            is OnboardingIntent.OnStartVerificationClicked -> handleStartVerificationClicked()
            is OnboardingIntent.OnFeedbackDismissed -> handleFeedbackDismissed()
        }
    }

    private fun handlePhoneChanged(input: String) {
        // Enforce max 8 digits & numeric only
        val sanitized = input.filter { it.isDigit() }.take(8)
        val validation = validatePhoneUseCase(sanitized)
        val error = if (validation is ValidationResult.Invalid && sanitized.isNotEmpty()) validation.errorMessage else null

        _uiState.update { state ->
            val updated = state.copy(phone = sanitized, phoneError = error)
            updated.copy(isSubmitEnabled = computeIsSubmitEnabled(updated))
        }
    }

    private fun handleCarnetChanged(input: String) {
        // Enforce max 10 digits & numeric only
        val sanitized = input.filter { it.isDigit() }.take(10)
        val validation = validateCarnetUseCase(sanitized)
        val error = if (validation is ValidationResult.Invalid && sanitized.isNotEmpty()) validation.errorMessage else null

        _uiState.update { state ->
            val updated = state.copy(carnet = sanitized, carnetError = error)
            updated.copy(isSubmitEnabled = computeIsSubmitEnabled(updated))
        }
    }

    private fun handleComplementChanged(input: String) {
        // Enforce max 2 characters alphanumeric uppercase
        val sanitized = input.filter { it.isLetterOrDigit() }.take(2).uppercase()
        val validation = validateComplementUseCase(sanitized)
        val error = if (validation is ValidationResult.Invalid && sanitized.isNotEmpty()) validation.errorMessage else null

        _uiState.update { state ->
            val updated = state.copy(complement = sanitized, complementError = error)
            updated.copy(isSubmitEnabled = computeIsSubmitEnabled(updated))
        }
    }

    private fun computeIsSubmitEnabled(state: OnboardingUiState): Boolean {
        val isPhoneValid = validatePhoneUseCase(state.phone) is ValidationResult.Valid
        val isCarnetValid = validateCarnetUseCase(state.carnet) is ValidationResult.Valid
        val isComplementValid = validateComplementUseCase(state.complement) is ValidationResult.Valid
        return isPhoneValid && isCarnetValid && isComplementValid && !state.isLoading
    }

    private fun handleSubmitClicked() {
        val state = _uiState.value
        if (!state.isSubmitEnabled) return

        if (locationPermissionGate.hasLocationPermission()) {
            executeSubmission()
        } else {
            _uiState.update { it.copy(showLocationRationale = true) }
        }
    }

    private fun handleRationaleDismissed() {
        _uiState.update { it.copy(showLocationRationale = false) }
    }

    private fun handleRationaleConfirmed() {
        _uiState.update { it.copy(showLocationRationale = false) }
    }

    private fun handleLocationPermissionResult(isGranted: Boolean) {
        if (isGranted) {
            executeSubmission()
        } else {
            _uiState.update { it.copy(userFeedbackMessage = "El permiso de ubicación es necesario para continuar.") }
        }
    }

    private fun executeSubmission() {
        val currentState = _uiState.value
        if (currentState.isLoading) return

        _uiState.update { it.copy(isLoading = true, isSubmitEnabled = false) }

        viewModelScope.launch {
            val registrationData = RegistrationData(
                phone = currentState.phone,
                carnet = currentState.carnet,
                complement = currentState.complement.ifBlank { null }
            )
            val result = submitRegistrationUseCase(registrationData)
            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, currentStep = OnboardingStep.AUTENTICACION) }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSubmitEnabled = computeIsSubmitEnabled(it),
                            userFeedbackMessage = error.message ?: "Ocurrió un error al procesar el registro."
                        )
                    }
                }
            )
        }
    }

    private fun handleTipPageChanged(pageIndex: Int) {
        _uiState.update { it.copy(activeTipIndex = pageIndex.coerceIn(0, it.totalTipsCount - 1)) }
    }

    private fun handleNextTipClicked() {
        val current = _uiState.value.activeTipIndex
        if (current < _uiState.value.totalTipsCount - 1) {
            _uiState.update { it.copy(activeTipIndex = current + 1) }
        }
    }

    private fun handleStartVerificationClicked() {
        _uiState.update { it.copy(isFlowCompleted = true) }
    }

    private fun handleFeedbackDismissed() {
        _uiState.update { it.copy(userFeedbackMessage = null) }
    }

    companion object {
        fun provideFactory(
            submitRegistrationUseCase: SubmitRegistrationUseCase,
            locationPermissionGate: LocationPermissionGate
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return OnboardingViewModel(
                    submitRegistrationUseCase = submitRegistrationUseCase,
                    locationPermissionGate = locationPermissionGate
                ) as T
            }
        }
    }
}
