package com.example.musicpracticejournal.domain.usecase.chunks

import com.example.musicpracticejournal.data.db.entity.MusicFragment
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.domain.core.UseCase
import com.example.musicpracticejournal.injection.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CreateMusicFragmentUseCase @Inject constructor(
    private val musicPracticeRepository: MusicPracticeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<CreateMusicFragmentUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) {
        musicPracticeRepository.savePracticeFragment(params.musicFragment)
    }

    data class Params(
        val musicFragment: MusicFragment
    )
}