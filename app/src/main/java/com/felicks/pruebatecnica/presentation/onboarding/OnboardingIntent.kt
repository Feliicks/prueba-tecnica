package com.felicks.pruebatecnica.presentation.onboarding

sealed interface OnboardingIntent {
    // Step 1: Form inputs
    data class OnPhoneChanged(val value: String) : OnboardingIntent
    data class OnCarnetChanged(val value: String) : OnboardingIntent
    data class OnComplementChanged(val value: String) : OnboardingIntent
    object OnSubmitClicked : OnboardingIntent

    // Step 1: Location rationale & permission handling
    object OnRationaleDismissed : OnboardingIntent
    object OnRationaleConfirmed : OnboardingIntent
    data class OnLocationPermissionResult(val isGranted: Boolean) : OnboardingIntent

    // Step 2: Educational carousel navigation
    data class OnTipPageChanged(val pageIndex: Int) : OnboardingIntent
    object OnNextTipClicked : OnboardingIntent
    object OnStartVerificationClicked : OnboardingIntent

    // General feedback clearing
    object OnFeedbackDismissed : OnboardingIntent
}
