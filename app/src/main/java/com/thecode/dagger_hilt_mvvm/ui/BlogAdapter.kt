package com.thecode.dagger_hilt_mvvm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thecode.dagger_hilt_mvvm.R
import com.thecode.dagger_hilt_mvvm.model.Blog
import kotlinx.android.synthetic.main.item_blog.view.*

class BlogAdapter(
    private val listener: BlogItemListener
) : ListAdapter<Blog, BlogAdapter.BlogViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<Blog>() {
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Blog, newItem: Blog) = oldItem.id == newItem.id
    }

    init {
        setHasStableIds(true)
    }

    interface BlogItemListener {
        fun onClickedBlog(blogTitle: CharSequence)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = BlogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_blog, parent, false), listener)
        return view
    }

    override fun getItemId(position: Int) = getItem(position).id

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = getItem(position)
        holder.textTitle.text = blog.title
        holder.textDescription.text = blog.body
        Glide.with(holder.itemLayout).load(blog.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .apply(RequestOptions().centerCrop())
            .into(holder.image)
    }

    class BlogViewHolder(itemView: View, private val listener: BlogItemListener) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val itemLayout: ConstraintLayout = itemView.blog_layout
        val textTitle: TextView = itemView.text_title
        val textDescription: TextView = itemView.text_description
        val image: AppCompatImageView = itemView.image

        init {
            itemLayout.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClickedBlog(textTitle.text)
        }
    }
}
