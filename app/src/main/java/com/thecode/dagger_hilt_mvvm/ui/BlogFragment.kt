package com.thecode.dagger_hilt_mvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thecode.dagger_hilt_mvvm.R
import com.thecode.dagger_hilt_mvvm.common.livedata.OneOffEvent
import com.thecode.dagger_hilt_mvvm.databinding.FragmentBlogBinding
import com.thecode.dagger_hilt_mvvm.model.Blog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlogFragment : Fragment(), BlogAdapter.BlogItemListener {

    private lateinit var binding: FragmentBlogBinding

    private val viewModel: BlogViewModel by viewModels()
    private val adapter: BlogAdapter by lazy { BlogAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentBlogBinding.inflate(layoutInflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        with(viewModel) {
            viewModelState.observe(viewLifecycleOwner, ::onViewStateChanged)
            navigationEvent.observe(viewLifecycleOwner, ::onNavigationEventChanged)
            refreshBlogs()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshBlogs()
        }

        binding.navigateToButton.setOnClickListener {
            viewModel.onNavigateToButtonPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.blogRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.blogRecyclerview.adapter = adapter
    }

    private fun onNavigationEventChanged(navigationEvent: OneOffEvent<BlogViewModel.NavigationEvent>) {
        navigationEvent.runOnce {
            when (it) {
                is BlogViewModel.NavigationEvent.GotoBlankPage -> {
                    findNavController().navigate(R.id.action_blogFragment_to_blankFragment)
                }
            }
        }
    }

    private fun onViewStateChanged(viewState: BlogViewModel.State) {

        when (viewState) {
            is BlogViewModel.State.LoadingBlogs -> {
                displayLoading(true)
            }
            is BlogViewModel.State.ReceivedBlogsOffline -> {
                displayLoading(false)
                populateRecyclerView(viewState.blogs)
                displayError(viewState.errorMsg)
            }
            is BlogViewModel.State.ReceivedBlogs -> {
                displayLoading(false)
                populateRecyclerView(viewState.blogs)
            }
        }
    }

    private fun displayError(message: String?) {
        if (message.isNullOrEmpty().not()) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayLoading(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
        binding.navigateToButton.isVisible = isLoading.not()
    }

    private fun populateRecyclerView(blogs: List<Blog>) {
        if (blogs.isNotEmpty()) {
            adapter.submitList(ArrayList(blogs))
        }
    }

    override fun onClickedBlog(blogTitle: CharSequence) {
        Toast.makeText(context, blogTitle, Toast.LENGTH_SHORT).show()
    }
}
