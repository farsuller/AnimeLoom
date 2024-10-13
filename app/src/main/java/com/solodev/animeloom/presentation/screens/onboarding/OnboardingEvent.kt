package com.solodev.animeloom.presentation.screens.onboarding

sealed class OnboardingEvent {
    data object SaveAppEntry : OnboardingEvent()
}
