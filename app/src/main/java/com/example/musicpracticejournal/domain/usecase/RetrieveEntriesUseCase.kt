package com.example.musicpracticejournal.domain.usecase

import com.example.musicpracticejournal.data.db.entity.MusicFragment
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.di.IoDispatcher
import com.example.musicpracticejournal.domain.core.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveEntriesUseCase @Inject constructor(
    private val musicPracticeRepository: MusicPracticeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<MusicFragment>>(dispatcher) {

    override suspend fun execute(params: Unit): Flow<List<MusicFragment>> {
        return musicPracticeRepository.getMusicFragments()
    }
}