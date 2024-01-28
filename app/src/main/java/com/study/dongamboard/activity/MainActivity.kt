package com.study.dongamboard.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.study.dongamboard.R
import com.study.dongamboard.activity.notice.NoticeListActivity
import com.study.dongamboard.activity.post.BestActivity
import com.study.dongamboard.activity.post.PostListActivity
import com.study.dongamboard.activity.user.UserActivity
import com.study.dongamboard.type.BoardCategoryType

class MainActivity : AppCompatActivity() {

    init{
        instance = this
    }

    companion object {
        val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(
            name = "TOKEN_DATA_STORE"
        )
        lateinit var instance: MainActivity
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
    }

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
            val intent = Intent(applicationContext, UserActivity::class.java)
            startActivity(intent)
        }
    }
}