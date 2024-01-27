package com.study.dongamboard.adapter

import android.view.View
import android.widget.TextView
import com.study.dongamboard.R

class PostHolder(root: View) {
    var tvPostTitle: TextView
    var tvLikes: TextView

    init {
        tvPostTitle = root.findViewById(R.id.tvPostlistTitle)
        tvLikes = root.findViewById(R.id.tvPostlistLikes)
    }
}