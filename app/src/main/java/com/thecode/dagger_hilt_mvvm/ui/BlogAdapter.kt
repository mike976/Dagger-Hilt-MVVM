package com.thecode.dagger_hilt_mvvm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.thecode.dagger_hilt_mvvm.model.Blog

class BlogAdapter(
    private val listener: BlogItemListener
) : ListAdapter<Blog, BlogViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<Blog>() {
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Blog, newItem: Blog) = oldItem.id == newItem.id
    }

    init {
        setHasStableIds(true)
    }

    interface BlogItemListener {
        fun onClickedBlog(blogTitle: CharSequence, currentList: List<Blog>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BlogViewHolder(inflater, parent)
    }

    override fun getItemId(position: Int) = getItem(position).id

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.bind(getItem(position), listener, currentList)
    }
}
