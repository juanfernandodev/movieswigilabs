package com.juanferdev.pruebaingresomovieswigilabs.core.network

import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.features.UiState

suspend fun <T> makeNetworkCall(
    call: suspend (() -> T)
): UiState<T> = try {
    UiState.Success(call())
} catch (e: Exception) {
    UiState.Error(R.string.there_was_error)
}

