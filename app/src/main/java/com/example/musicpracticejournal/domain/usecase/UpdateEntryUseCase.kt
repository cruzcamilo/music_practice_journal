package com.example.musicpracticejournal.domain.usecase

import com.example.musicpracticejournal.data.db.entity.MusicFragment
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.di.IoDispatcher
import com.example.musicpracticejournal.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateEntryUseCase @Inject constructor(
    private val musicPracticeRepository: MusicPracticeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<UpdateEntryUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) {
        musicPracticeRepository.updatePracticeFragment(params.musicFragment)
    }

    data class Params(
        val musicFragment: MusicFragment
    )
}