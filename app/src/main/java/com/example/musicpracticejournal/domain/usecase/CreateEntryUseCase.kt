package com.example.musicpracticejournal.domain.usecase

import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.di.IoDispatcher
import com.example.musicpracticejournal.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CreateEntryUseCase @Inject constructor(
    private val musicPracticeRepository: MusicPracticeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<CreateEntryUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) {
        musicPracticeRepository.savePracticeFragment(params.entry)
    }

    data class Params(
        val entry: Entry
    )
}