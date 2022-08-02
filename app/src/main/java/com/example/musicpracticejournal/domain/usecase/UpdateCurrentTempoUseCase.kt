package com.example.musicpracticejournal.domain.usecase

import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.di.IoDispatcher
import com.example.musicpracticejournal.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateCurrentTempoUseCase @Inject constructor(
    private val musicPracticeRepository: MusicPracticeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<UpdateCurrentTempoUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) {
        musicPracticeRepository.updateCurrentTempo(params.currentTempo, params.id)
    }

    data class Params(
        val currentTempo: Int,
        val id: Long
    )
}