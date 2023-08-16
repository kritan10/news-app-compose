package com.example.newsapp.events

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackbar(val message: String) : UiEvent()
    object NavigateUp : UiEvent()
}