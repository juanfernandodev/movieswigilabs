package com.juanferdev.pruebaingresomovieswigilabs.api

sealed class UiState<T> {
    class Success<T>(val data: T) : UiState<T>()
    class Loading<T> : UiState<T>()
    class Error<T>(val messageId: Int) : UiState<T>()
}