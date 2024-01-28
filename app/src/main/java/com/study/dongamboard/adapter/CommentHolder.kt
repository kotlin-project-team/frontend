package com.study.dongamboard.adapter

import android.view.View
import android.widget.TextView
import com.study.dongamboard.R

class CommentHolder(root: View) {
    var tvCmtNickname: TextView
    var tvCmtContent: TextView

    init {
        tvCmtNickname = root.findViewById(R.id.tvCmtNickname)
        tvCmtContent = root.findViewById(R.id.tvCmtContent)
    }
}