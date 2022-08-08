package com.example.musicpracticejournal.domain.usecase

import com.example.musicpracticejournal.data.repository.EntryRepository
import com.example.musicpracticejournal.di.IoDispatcher
import com.example.musicpracticejournal.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateOriginalTempoUseCase @Inject constructor(
    private val entryRepository: EntryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<UpdateOriginalTempoUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) {
        entryRepository.updateOriginalTempo(params.originalTempo, params.id)
    }

    data class Params(
        val originalTempo: Int,
        val id: Long
    )
}