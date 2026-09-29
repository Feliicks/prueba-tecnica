package com.felicks.pruebatecnica.presentation.onboarding

data class AuthTip(
    val title: String,
    val description: String,
    val iconResId: Int? = null
)

data class OnboardingUiState(
    // Step 1: Form input values
    val phone: String = "",
    val carnet: String = "",
    val complement: String = "",

    // Validation error messages (null when valid or untouched)
    val phoneError: String? = null,
    val carnetError: String? = null,
    val complementError: String? = null,

    // Controls submit action availability
    val isSubmitEnabled: Boolean = false,

    // Location rationale visibility
    val showLocationRationale: Boolean = false,

    // Network / service submission progress
    val isLoading: Boolean = false,

    // Flow navigation state
    val currentStep: OnboardingStep = OnboardingStep.INFORMACION,

    // Step 2: Carousel state
    val activeTipIndex: Int = 0,
    val totalTipsCount: Int = 2,
    val tips: List<AuthTip> = listOf(
        AuthTip(
            title = "Prepárate para tu foto",
            description = "Busca un lugar con buena iluminación natural o artificial. Retira gorras, gafas oscuras o accesorios que cubran tu rostro."
        ),
        AuthTip(
            title = "Ten a mano tu carnet",
            description = "Ubica tu cédula de identidad sobre una superficie plana. Evita reflejos de luz y asegúrate de que todos los datos sean legibles."
        )
    ),

    // Transient feedback messages (Snackbar / Toast notifications)
    val userFeedbackMessage: String? = null,
    val isFlowCompleted: Boolean = false
) {
    val isLastTip: Boolean
        get() = activeTipIndex >= totalTipsCount - 1
}
