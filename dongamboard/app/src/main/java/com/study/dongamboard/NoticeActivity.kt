package com.study.dongamboard

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.model.NoticeData

class NoticeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        val notice = intent.getSerializableExtra("noticeData") as NoticeData?

        val tvNoticeTitle = findViewById<TextView>(R.id.tvNoticeTitle)
        val tvNoticeContent = findViewById<TextView>(R.id.tvNoticeContent)

        tvNoticeTitle.text = notice?.title
        tvNoticeContent.text = notice?.content

    }

}