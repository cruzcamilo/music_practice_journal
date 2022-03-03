package com.example.musicpracticejournal.practicefragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.data.db.entity.MusicFragment
import com.example.musicpracticejournal.databinding.MusicFragmentItemBinding
import com.example.musicpracticejournal.util.setBpmInformation

class PracticeFragmentAdapter(
    private val onItemClickListener: (practiceFragmentId: Long) -> Unit
) :
    ListAdapter<MusicFragment, PracticeFragmentAdapter.ViewHolder>(MusicFragmentDiffCallback) {

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
                binding.practiceFragmentLastPracticeTv.text =
                    binding.practiceFragmentLastPracticeTv.context.resources.getString(
                        R.string.last_practice_item,
                        updated ?: ""
                    )
                binding.practiceFragmentCurrentTempo.text =
                    currentTempo.setBpmInformation(binding.practiceFragmentCurrentTempo.context)
                binding.musicFragmentTargetTempo.text =
                    targetTempo.setBpmInformation(binding.musicFragmentTargetTempo.context)

                itemView.setOnClickListener {
                    this?.id?.let { onItemClickListener.invoke(it) }
                }
            }
        }
    }

    object MusicFragmentDiffCallback : DiffUtil.ItemCallback<MusicFragment>() {
        override fun areItemsTheSame(oldItem: MusicFragment, newItem: MusicFragment): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MusicFragment, newItem: MusicFragment): Boolean {
            return oldItem == newItem
        }
    }
}