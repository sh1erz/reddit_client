package com.example.redditclient.model

data class Thing<T : Data>(val kind: String, val data: T)

