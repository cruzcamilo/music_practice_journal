package com.example.musicpracticejournal.domain.usecase

import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.data.repository.EntryRepository
import com.example.musicpracticejournal.di.IoDispatcher
import com.example.musicpracticejournal.domain.core.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveEntriesUseCase @Inject constructor(
    private val entryRepository: EntryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<Entry>>(dispatcher) {

    override suspend fun execute(params: Unit): Flow<List<Entry>> {
        return entryRepository.getMusicFragments()
    }
}