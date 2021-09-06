package com.thecode.dagger_hilt_mvvm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.thecode.dagger_hilt_mvvm.databinding.ActivityMainBinding
import com.thecode.dagger_hilt_mvvm.model.Blog
import com.thecode.dagger_hilt_mvvm.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BlogAdapter.BlogItemListener {

    private val viewModel: MainViewModel by viewModels()
    private val adapter: BlogAdapter by lazy { BlogAdapter(this) }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupRecyclerView()
        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvents)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.setStateEvent(MainStateEvent.GetBlogEvents)
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Blog>> -> {
                    displayLoading(false)
                    populateRecyclerView(dataState.data)
                }
                is DataState.Loading -> {
                    displayLoading(true)
                }
                is DataState.Error -> {
                    displayLoading(false)
                    displayError(dataState.exception.message)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message.isNullOrEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayLoading(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun populateRecyclerView(blogs: List<Blog>) {
        if (blogs.isNotEmpty()) adapter.submitList(ArrayList(blogs))
    }

    private fun setupRecyclerView() {
        binding.blogRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.blogRecyclerview.adapter = adapter
    }

    override fun onClickedBlog(blogTitle: CharSequence) {
        Toast.makeText(this, blogTitle, Toast.LENGTH_SHORT).show()
    }
}
