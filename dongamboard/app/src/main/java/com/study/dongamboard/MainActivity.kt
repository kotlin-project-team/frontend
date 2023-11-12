package com.study.dongamboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

        val btnBoardGeneral = findViewById<Button>(R.id.btnBoardGeneral)
        btnBoardGeneral.setOnClickListener {
            val intent = Intent(this, PostListActivity::class.java)
            intent.putExtra("postCategory", "자유게시판")
            startActivity(intent)
        }

        val btnBoardMarket = findViewById<Button>(R.id.btnBoardMarket)
        btnBoardMarket.setOnClickListener {
            val intent = Intent(this, PostListActivity::class.java)
            intent.putExtra("postCategory", "장터게시판")
            startActivity(intent)
        }

        val ivUserPage = findViewById<ImageView>(R.id.ivUserPage)
        ivUserPage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}