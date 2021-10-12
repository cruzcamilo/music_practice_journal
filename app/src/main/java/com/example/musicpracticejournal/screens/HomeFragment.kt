package com.example.musicpracticejournal.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicpracticejournal.EventObserver
import com.example.musicpracticejournal.MusicPracticeApplication
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.data.PracticeFragment
import com.example.musicpracticejournal.databinding.FragmentHomeBinding
import com.example.musicpracticejournal.viewmodel.MainActivityViewModelFactory
import com.example.musicpracticejournal.viewmodel.MusicFragmentViewModel


class HomeFragment : Fragment() {

    private val viewModel by viewModels<MusicFragmentViewModel> {
        MainActivityViewModelFactory((requireContext().applicationContext as MusicPracticeApplication).repository, (requireContext().applicationContext as MusicPracticeApplication).timerUseCase)
    }
    private lateinit var binding : FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var musicFragmentAdapter: MusicFragmentAdapter

    companion object {
        val MUSIC_FRAGMENT_KEY = "music_fragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.deleteAllMusicFragments()
        navController = Navigation.findNavController(binding.root)
        binding.rvFragments.layoutManager = LinearLayoutManager(context)
        musicFragmentAdapter = MusicFragmentAdapter { musicFragment->
            viewModel.startPractice(musicFragment)

        }
        binding.rvFragments.adapter = musicFragmentAdapter
        setupFab()
        setupNavigation()
        observePracticeFragmentList()
    }

    private fun observePracticeFragmentList() {
        viewModel.practiceFragments.observe(viewLifecycleOwner) { musicFragments ->
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

    private fun setupFab() {
        binding.fabButton.setOnClickListener {
            viewModel.createPracticeFragment()
        }
    }

    private fun setupNavigation() {
        viewModel.createPracticeFragmentEvent.observe(viewLifecycleOwner, EventObserver{
            navController.navigate(R.id.action_home_to_createFragment)
        })
        viewModel.startPracticeEvent.observe(viewLifecycleOwner, EventObserver{
            val bundle = bundleOf(MUSIC_FRAGMENT_KEY to it)
            navController.navigate(R.id.action_home_to_practiceFragment, bundle)
        })
    }

    class MusicFragmentAdapter(private val onFragmentClickListener: (PracticeFragment) -> Unit) :
        RecyclerView.Adapter<MusicFragmentAdapter.MusicFragmentViewHolder>() {

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
}