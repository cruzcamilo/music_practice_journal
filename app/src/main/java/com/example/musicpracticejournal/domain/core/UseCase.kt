package com.example.musicpracticejournal.domain.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in Params, Response>(
    private val coroutineDispatcher: CoroutineDispatcher
) {
    protected abstract suspend fun execute(params: Params): Response

    suspend operator fun invoke(params: Params): Response {
        return withContext(coroutineDispatcher) {
            val result = execute(params)
            result
        }
    }
}