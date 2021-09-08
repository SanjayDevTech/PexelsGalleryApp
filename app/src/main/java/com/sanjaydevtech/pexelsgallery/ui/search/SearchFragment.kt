package com.sanjaydevtech.pexelsgallery.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.sanjaydevtech.pexelsgallery.adapter.PhotoComparator
import com.sanjaydevtech.pexelsgallery.adapter.PhotoListAdapter
import com.sanjaydevtech.pexelsgallery.adapter.PhotoLoadStateAdapter
import com.sanjaydevtech.pexelsgallery.databinding.FragmentSearchBinding
import com.sanjaydevtech.pexelsgallery.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()
    private val pagingAdapter = PhotoListAdapter(PhotoComparator)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("onCreateView")
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmer.isVisible = false
        binding.photosListView.isVisible = true
        binding.photosListView.apply {
            adapter = pagingAdapter.withLoadStateFooter(
                PhotoLoadStateAdapter {
                    pagingAdapter.retry()
                }
            )
            setHasFixedSize(true)
        }
        viewModel.query.observe(viewLifecycleOwner) { query ->
            val start = binding.searchQuery.selectionStart
            val end = binding.searchQuery.selectionEnd
            binding.searchQuery.setText(query)
            binding.searchQuery.setSelection(start, end)
        }
        viewModel.searchPhotos.observe(viewLifecycleOwner) { pagingData ->
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
        viewModel.rvEvent.observe(viewLifecycleOwner) { event ->
            if (event == "search") {
                binding.photosListView.smoothScrollToPosition(0)
            }
        }
        binding.searchButton.setOnClickListener {
            val query = binding.searchQuery.text.toString()
            if (query.isNotBlank()) {
                binding.shimmer.startShimmer()
                binding.shimmer.isVisible = true
                binding.photosListView.isVisible = false
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(2000)
                    binding.shimmer.isVisible = false
                    binding.photosListView.isVisible = true
                }
            }
            viewModel.setQuery(query)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}