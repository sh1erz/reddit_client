package com.example.redditclient.view.adapter

import java.util.*

fun redditTime(time: Long) : Int {
    val createdAgo = Date().time - time
    //days
    //hours
    //minutes
    return (createdAgo / (3600 * 1000L)).toInt()
}