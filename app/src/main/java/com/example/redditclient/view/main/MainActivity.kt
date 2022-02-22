package com.example.redditclient.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.redditclient.databinding.ActivityMainBinding
import com.example.redditclient.view.ViewModelsFactory
import com.example.redditclient.view.adapter.PostAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> { ViewModelsFactory() }
    private val adapter = PostAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        fetchPosts()
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    private fun fetchPosts() {
        disposables.add(viewModel.fetchPostsObservable().subscribe {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        })
    }
}