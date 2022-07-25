package com.example.musicpracticejournal.domain.usecase.chunks

import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.domain.core.UseCase
import com.example.musicpracticejournal.injection.coroutines.IoDispatcher
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