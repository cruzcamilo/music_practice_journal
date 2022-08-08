package com.example.musicpracticejournal.domain.usecase

import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.data.repository.EntryRepository
import com.example.musicpracticejournal.di.IoDispatcher
import com.example.musicpracticejournal.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateEntryUseCase @Inject constructor(
    private val entryRepository: EntryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<UpdateEntryUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) {
        entryRepository.updateEntry(params.entry)
    }

    data class Params(
        val entry: Entry
    )
}