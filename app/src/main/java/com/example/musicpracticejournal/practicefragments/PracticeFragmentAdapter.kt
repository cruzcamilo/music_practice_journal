package com.example.musicpracticejournal.practicefragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.setBpmInformation

class PracticeFragmentAdapter(private val onFragmentClickListener: (PracticeFragment) -> Unit) :
    RecyclerView.Adapter<PracticeFragmentAdapter.MusicFragmentViewHolder>() {

    private var practiceFragmentsList : List<PracticeFragment> = ArrayList(0)

    inner class MusicFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fragmentType: AppCompatTextView = view.findViewById(R.id.practice_fragment_type_tv)
        val title: AppCompatTextView = view.findViewById(R.id.practice_fragment_title_tv)
        val currentTempo: AppCompatTextView = view.findViewById(R.id.practice_fragment_current_tempo)
        val lastPractice: AppCompatTextView = view.findViewById(R.id.practice_fragment_last_practice_tv)
        val targetTempo: AppCompatTextView = view.findViewById(R.id.music_fragment_target_tempo)
    }

    fun bindData(practiceFragments: List<PracticeFragment>) {
        practiceFragmentsList = ArrayList(practiceFragments)
        Log.d("Home Fragment - List", practiceFragments.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicFragmentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_fragment_item, parent, false)
        return MusicFragmentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MusicFragmentViewHolder, position: Int) {
        holder.fragmentType.text = practiceFragmentsList[position].type
        holder.title.text = holder.title.context.resources
            .getString(R.string.title, practiceFragmentsList[position].author, practiceFragmentsList[position].name)
        holder.lastPractice.visibility = if (practiceFragmentsList[position].updated.isNullOrEmpty()) View.GONE else View.VISIBLE
        holder.lastPractice.text = holder.title.context.resources.getString(R.string.last_practice_item, practiceFragmentsList[position].updated?:"")
        holder.currentTempo.text = practiceFragmentsList[position].currentTempo.setBpmInformation(holder.currentTempo.context)
        holder.targetTempo.text = practiceFragmentsList[position].targetTempo.setBpmInformation(holder.targetTempo.context)
        holder.itemView.setOnClickListener {
            onFragmentClickListener(practiceFragmentsList[position])
        }
    }

    override fun getItemCount(): Int {
        return practiceFragmentsList.size
    }
}