package com.example.redditclient.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.redditclient.databinding.ActivityMainBinding
import com.example.redditclient.view.ViewModelsFactory
import com.example.redditclient.view.adapter.PostAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> { ViewModelsFactory(applicationContext) }
    private val postAdapter = PostAdapter(this)
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.apply {
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
            adapter = postAdapter
        }
        with(binding.swipeRefresh) {
            setOnRefreshListener { postAdapter.refresh() }
            lifecycleScope.launch{
                postAdapter.onPagesUpdatedFlow.collect {
                    isRefreshing = false
                }
            }
        }
        showPosts()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun showPosts() {
        disposables.add(viewModel.posts.subscribe {
            lifecycleScope.launch {
                postAdapter.submitData(it)
            }
        })
    }
}