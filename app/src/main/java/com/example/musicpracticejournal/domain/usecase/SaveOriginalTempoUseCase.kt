package com.example.musicpracticejournal.domain.usecase

import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.di.IoDispatcher
import com.example.musicpracticejournal.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveOriginalTempoUseCase @Inject constructor(
    private val musicPracticeRepository: MusicPracticeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<SaveOriginalTempoUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) {
        musicPracticeRepository.updateOriginalTempo(params.originalTempo, params.id)
    }

    data class Params(
        val originalTempo: Int,
        val id: Long
    )
}