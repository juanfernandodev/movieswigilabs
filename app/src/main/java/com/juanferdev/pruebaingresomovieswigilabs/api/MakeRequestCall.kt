package com.juanferdev.pruebaingresomovieswigilabs.api

import com.juanferdev.pruebaingresomovieswigilabs.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val UNAUTHORIZED_ERROR_CODE = 401

suspend fun <T> makeNetworkCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    call: suspend (() -> T),
): UiState<T> =
    withContext(dispatcher) {
        try {
            UiState.Success(call())
        } catch (e: Exception) {
            UiState.Error(R.string.unknown_error)
        }

    }