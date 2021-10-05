package com.example.musicpracticejournal.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicpracticejournal.MusicPracticeApplication
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.data.MusicFragment
import com.example.musicpracticejournal.databinding.FragmentHomeBinding
import com.example.musicpracticejournal.viewmodel.MainActivityViewModelFactory
import com.example.musicpracticejournal.viewmodel.MusicPracticeViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var navController: NavController
    lateinit var viewModel: MusicPracticeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), MainActivityViewModelFactory((requireActivity().application as MusicPracticeApplication).repository))
            .get(MusicPracticeViewModel::class.java)

        //viewModel.deleteAllMusicFragments()
        navController = Navigation.findNavController(binding.root)

        binding.rvFragments.layoutManager = LinearLayoutManager(context)
        val musicFragmentAdapter = MusicFragmentAdapter {
            navController.navigate(R.id.action_home_to_practiceFragment)
        }
        binding.rvFragments.adapter = musicFragmentAdapter

        binding.fabButton.setOnClickListener {
            navController.navigate(R.id.action_home_to_createFragment)
        }

        viewModel.musicFragments.observe(viewLifecycleOwner) { musicFragments ->
            viewModel.updateEmptyListText(musicFragments)
            musicFragmentAdapter.bindData(musicFragments)
        }

        viewModel.emptyMessageVisibility.observe(viewLifecycleOwner) {
            binding.emptyListMessage.apply {
                if (it) {
                    this.visibility = View.VISIBLE
                } else {
                    this.visibility = View.INVISIBLE
                }
            }
        }
    }

    class MusicFragmentAdapter(private val onFragmentClickListener: (MusicFragment) -> Unit) :
        RecyclerView.Adapter<MusicFragmentAdapter.MusicFragmentViewHolder>() {

        private var musicFragmentsList : List<MusicFragment> = ArrayList(0)

        inner class MusicFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val author: AppCompatTextView = view.findViewById(R.id.music_fragment_author)
            val name: AppCompatTextView = view.findViewById(R.id.music_fragment_name)
            val date: AppCompatTextView = view.findViewById(R.id.music_fragment_date)
            val practiceTime: AppCompatTextView = view.findViewById(R.id.music_fragment_practice_time)
        }

        fun bindData(musicFragments: List<MusicFragment>) {
            musicFragmentsList = ArrayList(musicFragments)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicFragmentViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.music_fragment_item, parent, false)
            return MusicFragmentViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MusicFragmentViewHolder, position: Int) {
            holder.author.text = musicFragmentsList[position].author
            holder.name.text = musicFragmentsList[position].name
            holder.date.text = musicFragmentsList[position].practiceDate
            holder.practiceTime.text = musicFragmentsList[position].practiceTime + " mins"
            holder.itemView.setOnClickListener {
                onFragmentClickListener.invoke(musicFragmentsList[position])
            }
        }

        override fun getItemCount(): Int {
            return musicFragmentsList.size
        }
    }
}