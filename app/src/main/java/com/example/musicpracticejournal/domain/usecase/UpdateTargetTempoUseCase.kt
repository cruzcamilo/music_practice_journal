package com.example.musicpracticejournal.domain.usecase

import com.example.musicpracticejournal.data.repository.EntryRepository
import com.example.musicpracticejournal.di.IoDispatcher
import com.example.musicpracticejournal.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateTargetTempoUseCase @Inject constructor(
    private val entryRepository: EntryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<UpdateTargetTempoUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) {
        entryRepository.updateTargetTempo(params.targetTempo, params.id)
    }

    data class Params(
        val targetTempo: Int,
        val id: Long
    )
}