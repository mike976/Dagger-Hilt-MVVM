package com.thecode.dagger_hilt_mvvm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thecode.dagger_hilt_mvvm.R
import com.thecode.dagger_hilt_mvvm.databinding.ItemBlogBinding
import com.thecode.dagger_hilt_mvvm.model.Blog

class BlogViewHolder(
    private val binding: ItemBlogBinding
) : RecyclerView.ViewHolder(binding.root) {

    constructor(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : this(ItemBlogBinding.inflate(inflater, parent, false))

    fun bind(item: Blog, listener: BlogAdapter.BlogItemListener, currentList: List<Blog>) {
        itemView.setOnClickListener { listener.onClickedBlog(binding.textTitle.text, currentList) }
        binding.textTitle.text = item.title
        binding.textDescription.text = item.body

        Glide.with(itemView.context).load(item.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .apply(RequestOptions().centerCrop())
            .into(binding.image)
    }
}
