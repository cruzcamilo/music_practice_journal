package com.example.musicpracticejournal.domain.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in Params, Response>(
    private val coroutineDispatcher: CoroutineDispatcher
) {

    protected abstract suspend fun execute(params: Params): Flow<Response>

    suspend operator fun invoke(params: Params): Flow<Response> {
        return execute(params)
            .distinctUntilChanged()
            .flowOn(coroutineDispatcher)
    }
}