package com.study.dongamboard.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.study.dongamboard.R
import com.study.dongamboard.activity.notice.NoticeListActivity
import com.study.dongamboard.activity.post.BestActivity
import com.study.dongamboard.activity.post.PostListActivity
import com.study.dongamboard.activity.user.SignInActivity
import com.study.dongamboard.type.BoardCategoryType

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
            intent.putExtra("postCategory", BoardCategoryType.자유게시판)
            startActivity(intent)
        }

        val btnBoardMarket = findViewById<Button>(R.id.btnBoardMarket)
        btnBoardMarket.setOnClickListener {
            val intent = Intent(this, PostListActivity::class.java)
            intent.putExtra("postCategory", BoardCategoryType.장터게시판)
            startActivity(intent)
        }

        val ivUserPage = findViewById<ImageView>(R.id.ivUserPage)
        ivUserPage.setOnClickListener {
            //TODO: 로그인(token 확인) 처리에 따라 변경
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}