package com.thecode.dagger_hilt_mvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.thecode.dagger_hilt_mvvm.R
import com.thecode.dagger_hilt_mvvm.common.showViewId
import com.thecode.dagger_hilt_mvvm.databinding.FragmentBlankBinding
import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.SelectedBlogsFragment
import kotlinx.coroutines.delay

class BlankFragment : Fragment() {

    private lateinit var binding: FragmentBlankBinding

    companion object {
        private const val DELAY_IN_MILLISECONDS = 1000L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentBlankBinding.inflate(layoutInflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigateBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_blogFragment)
        }

        binding.loadSelectedBlogsButton.setOnClickListener {
            showSelectedBlogsDialog()
        }

        // Use of a ViewFlipper: useful to rebuild screen based on diff states.

        // Below Simulates some work e.g retreiving data from local db or remote db
        // whereby a viewmodel will return a LOADING Viewstate
        // (frag would call renderViewFlipper(R.id.loadingProgressBar)
        // then viewModel would return a LOADED ViewState
        // (frag would renderViewFlipper(R.id.loadedPageLayout)
        binding.blankViewFlipper.isVisible = true

        lifecycleScope.launchWhenCreated {
            renderViewFlipper(R.id.loadingProgressBar)

            delay(DELAY_IN_MILLISECONDS) // Simulates work

            renderViewFlipper(R.id.loadedPageLayout)
        }
    }

    private fun renderViewFlipper(@IdRes targetViewGroupId: Int) {
        binding?.apply {
            blankViewFlipper.showViewId(targetViewGroupId)
        }
    }

    private fun showSelectedBlogsDialog() {
        val fragmentManager = activity?.supportFragmentManager
        val dialogFragment = SelectedBlogsFragment.newInstance()
        fragmentManager?.let { dialogFragment.show(it, SelectedBlogsFragment.TAG) }
    }
}
