package com.example.musicpracticejournal.practicefragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.databinding.MusicFragmentItemBinding
import com.example.musicpracticejournal.domain.entity.EntryItem
import com.example.musicpracticejournal.extensions.visibleOrGone
import com.example.musicpracticejournal.util.setBpmInformation

class EntryAdapter(
    private val onItemClickListener: (entryId: Long) -> Unit
) : ListAdapter<EntryItem, EntryAdapter.ViewHolder>(EntryItemDiffCallback) {

    inner class ViewHolder(val binding : MusicFragmentItemBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = MusicFragmentItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                updateVisibility(holder, this)
                setStrings(holder, this)

                itemView.setOnClickListener {
                    this?.id?.let { onItemClickListener.invoke(it) }
                }
            }
        }
    }

    private fun setStrings(viewHolder: ViewHolder, entryItem: EntryItem) {
        viewHolder.binding.typeTv.text = entryItem.type
        viewHolder.binding.titleTv.text = context.getString(R.string.title, entryItem.author, entryItem.name)
        viewHolder.binding.lastPracticeTv.text = context.resources.getString(
            R.string.last_practice_item,
            entryItem.updated
        )

        viewHolder.binding.currentTempo.text = entryItem.currentTempo.setBpmInformation(context)
        viewHolder.binding.targetTempo.text = entryItem.targetTempo.setBpmInformation(context)
    }

    private fun updateVisibility(viewHolder: ViewHolder, entryItem: EntryItem) {
        viewHolder.binding.lastPracticeTv.visibility = entryItem.getUpdatedVisibility()
        viewHolder.binding.currentTempoLabel.visibility = visibleOrGone(entryItem.currentTempo.isNotEmpty())
        viewHolder.binding.targetTempoLabel.visibility = entryItem.getTempoVisibility()
        viewHolder.binding.currentTempo.visibility = entryItem.getTempoVisibility()
        viewHolder.binding.targetTempo.visibility = entryItem.getTempoVisibility()
    }

    object EntryItemDiffCallback : DiffUtil.ItemCallback<EntryItem>() {
        override fun areItemsTheSame(oldItem: EntryItem, newItem: EntryItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EntryItem, newItem: EntryItem): Boolean {
            return oldItem == newItem
        }
    }
}