package com.example.musicpracticejournal.practicefragments

import android.view.ContextThemeWrapper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.domain.entity.EntryItem
import com.example.musicpracticejournal.extensions.visibleOrGone
import com.google.android.material.card.MaterialCardView
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.LooperMode

@LooperMode(LooperMode.Mode.PAUSED)
@RunWith(AndroidJUnit4::class)
class EntryAdapterTest {

    private lateinit var adapter: EntryAdapter
    private lateinit var viewHolder : EntryAdapter.ViewHolder
    private val diffCallback = EntryAdapter.EntryItemDiffCallback

    private val context = ContextThemeWrapper(ApplicationProvider.getApplicationContext(),
        R.style.Theme_MusicPracticeJournal)

    val onItemClickListenerMock = mockk<(Long) -> Unit>(relaxed = true)

    @Before
    fun setup() {
        adapter = EntryAdapter { onItemClickListenerMock }
        viewHolder = adapter.onCreateViewHolder(MaterialCardView(context), 0)
        val entryList = listOf(fullEntryItem())
        adapter.submitList(entryList)
    }

    @Test
    fun `onBindViewHolder sets correct value`() {
        val expectedEntry = fullEntryItem()
        adapter.onBindViewHolder(viewHolder, 0)

        with(viewHolder.binding) {
            assertThat(typeTv.text).isEqualTo(expectedEntry.type)
            assertThat(titleTv.text).isEqualTo(expectedEntry.getTitle())
            assertThat(lastPracticeTv.text).isEqualTo(expectedEntry.getLastPractice(context))
            assertThat(currentTempo.text).isEqualTo(expectedEntry.setBpmText(expectedEntry.currentTempo))
            assertThat(targetTempo.text).isEqualTo(expectedEntry.setBpmText(expectedEntry.targetTempo))
        }
    }

    @Test
    fun `onBindViewHolder sets visibility`() {
        val expectedEntry = fullEntryItem()
        adapter.onBindViewHolder(viewHolder, 0)

        with(viewHolder.binding) {
            assertThat(lastPracticeTv.visibility).isEqualTo(expectedEntry.getUpdatedVisibility())
            assertThat(currentTempoLabel.visibility).isEqualTo(visibleOrGone(expectedEntry.currentTempo.isNotEmpty()))
            assertThat(targetTempoLabel.visibility).isEqualTo(expectedEntry.getTempoVisibility())
            assertThat(currentTempo.visibility).isEqualTo(expectedEntry.getTempoVisibility())
            assertThat(targetTempo.visibility).isEqualTo(expectedEntry.getTempoVisibility())
        }
    }

    @Test
    fun `diffCallback are item the same`() {
        val entry = fullEntryItem()
        val copyEntry = fullEntryItem().copy()
        assertThat(diffCallback.areItemsTheSame(entry, copyEntry)).isTrue()

        assertThat(diffCallback.areItemsTheSame(entry, noTempoEntryItem())).isFalse()
    }

    @Test
    fun `contents are the same`() {
        val entry = fullEntryItem()
        val copyEntry = fullEntryItem().copy()
        assertThat(diffCallback.areContentsTheSame(entry, copyEntry)).isTrue()

        assertThat(diffCallback.areContentsTheSame(entry, noTempoEntryItem())).isFalse()
    }


    private fun noTempoEntryItem() = EntryItem(
        "song",
        "bach",
        "prelude in D minor",
        "active",
        "",
        "",
        2,
        ""
    )

    private fun fullEntryItem() = EntryItem(
        "song",
        "bach",
        "prelude in D minor",
        "active",
        "140",
        "100",
        1,
        "today"
    )
}