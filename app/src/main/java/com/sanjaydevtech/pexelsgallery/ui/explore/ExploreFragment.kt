package com.sanjaydevtech.pexelsgallery.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.sanjaydevtech.pexelsgallery.R
import com.sanjaydevtech.pexelsgallery.adapter.PhotoComparator
import com.sanjaydevtech.pexelsgallery.adapter.PhotoListAdapter
import com.sanjaydevtech.pexelsgallery.adapter.PhotoLoadStateAdapter
import com.sanjaydevtech.pexelsgallery.databinding.FragmentExploreBinding
import com.sanjaydevtech.pexelsgallery.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding: FragmentExploreBinding
        get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()
    private val pagingAdapter = PhotoListAdapter(PhotoComparator)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmer.isVisible = true
        binding.photosListView.isVisible = false
        binding.shimmer.startShimmer()

        binding.photosListView.apply {
            adapter = pagingAdapter.withLoadStateFooter(
                PhotoLoadStateAdapter {
                    pagingAdapter.retry()
                }
            )
            setHasFixedSize(true)
        }
        viewModel.curatedPhotos.observe(viewLifecycleOwner) { pagingData ->
            viewLifecycleOwner.lifecycleScope.launch {
                delay(1000)
                binding.shimmer.isVisible = false
                binding.photosListView.isVisible = true
            }
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
        viewModel.rvEvent.observe(viewLifecycleOwner) { event ->
            if (event == "explore") {
                binding.photosListView.smoothScrollToPosition(0)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}