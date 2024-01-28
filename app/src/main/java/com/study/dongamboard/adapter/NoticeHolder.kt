package com.study.dongamboard.adapter

import android.view.View
import android.widget.TextView
import com.study.dongamboard.R

class NoticeHolder(root: View) {
    var tvNoticelistTitle: TextView

    init {
        tvNoticelistTitle = root.findViewById(R.id.tvNoticelistTitle)
    }
}