package com.study.dongamboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        val ivUserPage = findViewById<ImageView>(R.id.ivUserPage)
        ivUserPage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

}