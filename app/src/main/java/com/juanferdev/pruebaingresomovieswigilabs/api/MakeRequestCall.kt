package com.juanferdev.pruebaingresomovieswigilabs.api

import com.juanferdev.pruebaingresomovieswigilabs.R

suspend fun <T> makeNetworkCall(
    call: suspend (() -> T)
): UiState<T> = try {
    UiState.Success(call())
} catch (e: Exception) {
    UiState.Error(R.string.there_was_error)
}

