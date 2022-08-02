package com.example.musicpracticejournal.practicefragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.databinding.MusicFragmentItemBinding
import com.example.musicpracticejournal.util.setBpmInformation

class PracticeFragmentAdapter(
    private val onItemClickListener: (entryId: Long) -> Unit
) :
    ListAdapter<Entry, PracticeFragmentAdapter.ViewHolder>(MusicFragmentDiffCallback) {

    inner class ViewHolder(val binding : MusicFragmentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MusicFragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                binding.practiceFragmentTypeTv.text = type
                binding.practiceFragmentTitleTv.text =
                    binding.practiceFragmentLastPracticeTv.context.getString(
                        R.string.title,
                        author,
                        name
                    )
                if (updated == null) {
                    binding.practiceFragmentLastPracticeTv.visibility = View.GONE
                } else {
                    binding.practiceFragmentLastPracticeTv.text =
                        binding.practiceFragmentLastPracticeTv.context.resources.getString(
                            R.string.last_practice_item,
                            updated
                        )
                }

                if (targetTempo != null && targetTempo > 0) {
                    binding.practiceFragmentCurrentTempo.text =
                        currentTempo.setBpmInformation(binding.practiceFragmentCurrentTempo.context)
                    binding.musicFragmentTargetTempo.text =
                        targetTempo.setBpmInformation(binding.musicFragmentTargetTempo.context)
                } else {
                    binding.musicFragmentCurrentTempoLabel.visibility = View.GONE
                    binding.practiceFragmentTargetTempoLabel.visibility = View.GONE
                }

                itemView.setOnClickListener {
                    this?.id?.let { onItemClickListener.invoke(it) }
                }
            }
        }
    }

    object MusicFragmentDiffCallback : DiffUtil.ItemCallback<Entry>() {
        override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem == newItem
        }
    }
}