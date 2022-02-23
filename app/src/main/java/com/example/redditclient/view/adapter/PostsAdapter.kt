package com.example.redditclient.view.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.redditclient.R
import com.example.redditclient.databinding.ItemPostBinding
import com.example.redditclient.model.PostData
import com.example.redditclient.repository.DEBUG
import com.example.redditclient.view.ViewModelsFactory.Companion.REDDIT_DOMAIN

class PostAdapter(private val context: Context) :
    PagingDataAdapter<PostData, PostAdapter.PostViewHolder>(DIFF_UTIL) {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position) ?: return
        holder.bind(post)
        holder.binding.root.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(REDDIT_DOMAIN + post.url))
            context.startActivity(browserIntent)
        }
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

    class PostViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostData) {
            with(binding) {
                title.text = post.title
                author.setFormattedText(R.string.author, post.author)
                subreddit.setFormattedText(R.string.subreddit, post.subreddit)
                time.setFormattedText(R.string.time, getTimeAgo(post.created), "hours")
                comments.text = post.comments.toString()
                rating.text = post.score.toString()

                thumbnail.visibility = View.VISIBLE
                if (post.thumbnailUrl?.startsWith("http") == true) {
                    Glide.with(thumbnail)
                        .load(post.thumbnailUrl)
                        .override(SIZE_ORIGINAL, 140)
                        .addListener(requestListener)
                        .placeholder(R.drawable.img_placeholder)
                        .error(R.drawable.img_placeholder)
                        .into(thumbnail)
                } else {
                    thumbnail.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PostData>() {
            override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean {
                return oldItem.thumbnailUrl == newItem.thumbnailUrl
            }
        }

        private val requestListener = object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                Log.i(DEBUG, e?.message ?: "Failed load image")
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        }

    }

}