package com.study.dongamboard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        val btnTextNotice = findViewById<TextView>(R.id.textNotice)
        btnTextNotice.setOnClickListener {
            val intent = Intent(this, NoticeListActivity::class.java)
            startActivity(intent)
        }

        val btnTextBest = findViewById<TextView>(R.id.textBest)
        btnTextBest.setOnClickListener {
            val intent = Intent(this, BestActivity::class.java)
            startActivity(intent)
        }

        val btnTextTalk = findViewById<TextView>(R.id.textTalk)
        btnTextTalk.setOnClickListener {
            val intent = Intent(this, PostListActivity::class.java)
            startActivity(intent)
        }

    }

}