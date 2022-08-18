package com.example.musicpracticejournal.domain.usecase

import com.example.musicpracticejournal.data.repository.EntryRepository
import com.example.musicpracticejournal.di.IoDispatcher
import com.example.musicpracticejournal.domain.core.FlowUseCase
import com.example.musicpracticejournal.domain.entity.EntryItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveEntriesUseCase @Inject constructor(
    private val entryRepository: EntryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<EntryItem>>(dispatcher) {

    override suspend fun execute(params: Unit): Flow<List<EntryItem>> {
        return entryRepository.getMusicFragments()
    }
}