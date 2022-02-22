package com.example.redditclient.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.redditclient.R
import com.example.redditclient.databinding.ItemPostBinding
import com.example.redditclient.model.PostData

class PostAdapter : PagingDataAdapter<PostData, PostAdapter.PostViewHolder>(DIFF_UTIL) {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position) ?: return
        holder.bind(post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostData) {
            with(binding) {
                authorAndTime.apply {
                    text = String.format(
                        context.resources.getString(R.string.author_and_time),
                        post.author,
                        redditTime(post.created),
                        "hours"
                    )
                }
                rating.text = "${post.score}k"
                subreddit.text = "r/${post.subreddit}"
                title.text = post.title
                comments.apply {
                    text =
                        String.format(context.resources.getString(R.string.comments, post.comments))
                }
                post.thumbnail?.let {
                    thumbnail.load(it) {
                        crossfade(true)
                        listener(
                            onError = { _, throwable ->
                                Log.e("EXCEPTIONS_TAG", throwable.message + "image not loaded $it")
                                thumbnail.visibility = View.GONE
                            }
                        )
                    }
                } ?: {
                    thumbnail.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PostData>() {
            override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean {
                return oldItem == newItem
            }
        }

    }

}