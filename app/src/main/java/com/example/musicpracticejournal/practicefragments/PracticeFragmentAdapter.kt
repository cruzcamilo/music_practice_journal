package com.example.musicpracticejournal.practicefragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicpracticejournal.R

class PracticeFragmentAdapter(private val onFragmentClickListener: (PracticeFragment) -> Unit) :
    RecyclerView.Adapter<PracticeFragmentAdapter.MusicFragmentViewHolder>() {

    private var practiceFragmentsList : List<PracticeFragment> = ArrayList(0)

    inner class MusicFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author: AppCompatTextView = view.findViewById(R.id.music_fragment_author)
        val name: AppCompatTextView = view.findViewById(R.id.music_fragment_name)
        val date: AppCompatTextView = view.findViewById(R.id.music_fragment_date)
        val practiceTime: AppCompatTextView = view.findViewById(R.id.music_fragment_practice_time)
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
        holder.author.text = practiceFragmentsList[position].author
        holder.name.text = practiceFragmentsList[position].name
        holder.date.text = practiceFragmentsList[position].practiceDate
        holder.practiceTime.text = holder.practiceTime.context.resources
            .getString(R.string.mins_amount, practiceFragmentsList[position].practiceTime )
        holder.itemView.setOnClickListener {
            onFragmentClickListener(practiceFragmentsList[position])
        }
    }

    override fun getItemCount(): Int {
        return practiceFragmentsList.size
    }
}