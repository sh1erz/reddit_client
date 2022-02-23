package com.example.redditclient.view.adapter

import android.widget.TextView
import java.util.Date


fun getTimeAgo(time: Long): Int {
    val createdAgoMs: Long = Date().time - time * 1000
    return (createdAgoMs / (3600 * 1000L)).toInt()
}

fun TextView.setFormattedText(stringId: Int, vararg args: Any) {
    this.apply {
        text = String.format(context.getString(stringId), *args)
    }
}
